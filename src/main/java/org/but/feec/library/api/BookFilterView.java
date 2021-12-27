package org.but.feec.library.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookFilterView {
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty isbn = new SimpleLongProperty();
    private final StringProperty bookTitle = new SimpleStringProperty();
    private final StringProperty authorName = new SimpleStringProperty();
    private final StringProperty authorSurname = new SimpleStringProperty();
    ////////////////
    public void setId(long id) {
        this.id.set(id);
    }

    public void setIsbn(long isbn) {
        this.isbn.set(isbn);
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public void setAuthorName(String authorName) {
        this.authorName.set(authorName);
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname.set(authorSurname);
    }
    ////////////////////////////
    public long getId() {
        return idProperty().get();
    }

    public long getIsbn() {
        return isbnProperty().get();
    }

    public String getAuthorName() {
        return authorNameProperty().get();
    }

    public String getBookTitle() {
        return bookTitleProperty().get();
    }
    public String getAuthorSurname() {
        return authorSurnameProperty().get();
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }
    public LongProperty isbnProperty() {
        return isbn;
    }

    public StringProperty authorNameProperty() {
        return authorName;
    }


    public StringProperty authorSurnameProperty() {
        return authorSurname;
    }

    public LongProperty idProperty() {
        return id;
    }

}

