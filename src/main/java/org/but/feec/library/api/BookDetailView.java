package org.but.feec.library.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookDetailView {
    private LongProperty id = new SimpleLongProperty();
    private LongProperty isbn = new SimpleLongProperty();
    private StringProperty bookTitle = new SimpleStringProperty();
    private StringProperty authorName = new SimpleStringProperty();
    private StringProperty authorSurname = new SimpleStringProperty();
    private StringProperty publishingHouse = new SimpleStringProperty();
    private StringProperty literatureCategory = new SimpleStringProperty();
    private StringProperty library = new SimpleStringProperty();


    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }


    public String getBookTitle() {
        return bookTitleProperty().get();
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitleProperty().setValue(bookTitle);
    }


    public Long getIsbn() {
        return isbnProperty().get();
    }

    public void setIsbn(Long isbn) {
        this.isbnProperty().setValue(isbn);
    }

    public String getAuthorName() {
        return authorNameProperty().get();
    }

    public void setAuthorName(String authorName) {
        this.authorNameProperty().setValue(authorName);
    }


    public String getAuthorSurname() {
        return authorSurnameProperty().get();
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurnameProperty().setValue(authorSurname);
    }


    public String getPublishingHouse() {
        return publishingHouseProperty().get();
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouseProperty().setValue(publishingHouse);
    }

    public String getLiteratureCategory() {
        return literatureCategoryProperty().get();
    }

    public void setLiteratureCategory(String literatureCategory) {
        this.literatureCategoryProperty().setValue(literatureCategory);
    }

    public String getLibrary() {
        return libraryProperty().get();
    }

    public void setLibrary(String library) {
        this.libraryProperty().setValue(library);
    }


    public LongProperty idProperty() {
        return id;
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

    public StringProperty publishingHouseProperty() {
        return publishingHouse;
    }

    public StringProperty literatureCategoryProperty() {
        return literatureCategory;
    }

    public StringProperty libraryProperty() {
        return library;
    }

}
