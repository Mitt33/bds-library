package org.but.feec.library.api;

public class BookEditView {

    private Long id;
    private Long isbn;
    private String bookTitle;
    private String authorName;
    private String authorSurname;
    private String publishingHouse;

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }
    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public String toString() {
        return "BookEditView{" +
              //  "id='" + id + '\'' +
                ", isbn='" + isbn + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorSurname='" + authorSurname + '\'' +
                ", publishingHouse='" + publishingHouse + '\'' +
                '}';
    }
}