package org.but.feec.library.services;

import org.but.feec.library.api.BookBasicView;
import org.but.feec.library.api.BookCreateView;
import org.but.feec.library.api.BookDetailView;
import org.but.feec.library.api.BookEditView;
import org.but.feec.library.data.BookRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDetailView getBookDetailView(Long id) {
        return bookRepository.findBookDetailedView(id);
    }

    public List<BookBasicView> getBookBasicView() {
        return bookRepository.getBookBasicView();
    }

    public void createBook(BookCreateView bookCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
//        char[] originalPassword = personCreateView.getPwd();
//        char[] hashedPassword = hashPassword(originalPassword);
//        personCreateView.setPwd(hashedPassword);

        bookRepository.createBook(bookCreateView);
    }

    public void editBook(BookEditView bookEditView) {
        bookRepository.editBook(bookEditView);
    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/patrickfav/bcrypt
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }


}
