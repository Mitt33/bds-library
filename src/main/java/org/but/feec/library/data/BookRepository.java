package org.but.feec.library.data;


import org.but.feec.library.api.*;
import org.but.feec.library.config.DataSourceConfig;
import org.but.feec.library.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public BookAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, pwd" +
                             " FROM library.member m" +
                             " LEFT JOIN library.member_contacts c on m.member_id = c.member_contacts_id " +
                             " WHERE c.email = ?")

        ) {
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);//??
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public BookDetailView findPersonDetailedView(Long bookId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select b.book_id, isbn, book_title, author_name, author_surname, publishing_house_name, category_name, library_name" +
                     " from library.book b " +
                     "left join library.book_has_author ba on b.book_id=ba.book_book_id "+
                     "left join library.author a on ba.author_author_id=a.author_id "+
                     "left join library.publishing_house ph on b.publishing_house_id=ph.publishing_house_id "+
                     "left join library.book_has_literature_category blc on b.book_id=blc.book_book_id "+
                     "left join library.literature_category lc on blc.book_book_id=lc.literature_category_id "+
                     "left join library.book_in_library bil on b.book_id=bil.book_id         "+
                     "left join library.library l on bil.book_id=l.library_id     "+
                     "WHERE b.book_id = ?")

        ) {
            preparedStatement.setLong(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find book by ID with additional info failed.", e);
        }
        return null;
    }

    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<BookBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select book_id, isbn, book_title, author_name, author_surname, publishing_house_name" +
                             " from library.book b"  +
                             " left join library.book_has_author ba on b.book_id=ba.book_book_id " +
                             " left join library.author a on ba.author_author_id=a.author_id " +
                             " left join library.publishing_house ph on b.publishing_house_id=ph.publishing_house_id ");

             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<BookBasicView> bookBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                bookBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return bookBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public void createBook(BookCreateView bookCreateView) {
        String insertPersonSQL = "begin;\n" +
                "\t insert into library.publishing_house (publishing_house_name) values (?);\n" +
                "\t insert into library.book (isbn, book_title, publishing_house_id, date_published) values (?,?,(select publishing_house_id from library.publishing_house where publishing_house_name=?), null);\n" +
                "\t insert into library.author (author_name, author_surname, date_birth, date_death) values (?,?, null, null);\n" +
                "\t insert into library.book_has_author(book_book_id, author_author_id) values ((SELECT book_id FROM library.book WHERE isbn=?),(SELECT author_id FROM library.author WHERE author_name =? and author_surname = ?));\n" +
                "commit;\n";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, bookCreateView.getPublishingHouse());
            preparedStatement.setLong(2,   bookCreateView.getIsbn());
            preparedStatement.setString(3, bookCreateView.getBookTitle());
            preparedStatement.setString(4, bookCreateView.getPublishingHouse());
            preparedStatement.setString(5, bookCreateView.getAuthorName());
            preparedStatement.setString(6, bookCreateView.getAuthorSurname());
            preparedStatement.setLong(7,   bookCreateView.getIsbn());
            preparedStatement.setString(8, bookCreateView.getAuthorName());
            preparedStatement.setString(9, bookCreateView.getAuthorSurname());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);

//            if (affectedRows == 0) {
//                throw new DataAccessException("Creating person failed, no rows affected.");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void editBook(BookEditView bookEditView) {
        String insertPersonSQL = "UPDATE bds.person p SET email = ?, first_name = ?, nickname = ?, surname = ? WHERE p.id_person = ?";
        String checkIfExists = "SELECT email FROM bds.person p WHERE p.id_person = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, bookEditView.getEmail());
           // preparedStatement.setString(2, bookEditView.getFirstName());
            preparedStatement.setString(3, bookEditView.getNickname());
           // preparedStatement.setString(4, bookEditView.getSurname());
            preparedStatement.setLong(5, bookEditView.getId());

            try {
                // TODO set connection autocommit to false
                /* HERE */
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, bookEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private BookAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        BookAuthView person = new BookAuthView();
        person.setEmail(rs.getString("email"));
        person.setPassword(rs.getString("pwd"));
        return person;
    }


    private BookBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        BookBasicView bookBasicView = new BookBasicView();
        bookBasicView.setId(rs.getLong("book_id"));
        bookBasicView.setIsbn(rs.getLong("isbn"));
        bookBasicView.setBookTitle(rs.getString("book_title"));
        bookBasicView.setAuthorName(rs.getString("author_name"));
        bookBasicView.setAuthorSurname(rs.getString("author_surname"));
        bookBasicView.setPublishingHouse(rs.getString("publishing_house_name"));
        return bookBasicView;
    }

    private BookDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        BookDetailView bookDetailView = new BookDetailView();
        bookDetailView.setId(rs.getLong("book_id"));
        bookDetailView.setIsbn(rs.getLong("isbn"));
        bookDetailView.setBookTitle(rs.getString("book_title"));
        bookDetailView.setAuthorName(rs.getString("author_name"));
        bookDetailView.setAuthorSurname(rs.getString("author_surname"));
        bookDetailView.setPublishingHouse(rs.getString("publishing_house_name"));
        bookDetailView.setLiteratureCategory(rs.getString("category_name"));
        bookDetailView.setLibrary(rs.getString("library_name"));

        return bookDetailView;
    }
}
