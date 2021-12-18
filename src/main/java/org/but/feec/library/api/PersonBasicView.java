package org.but.feec.library.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonBasicView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty city = new SimpleStringProperty();
    private LongProperty isbn = new SimpleLongProperty();
    private StringProperty givenName = new SimpleStringProperty();
    private StringProperty familyName = new SimpleStringProperty();
    private StringProperty nickname = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public Long getIsbn() {
        return isbnProperty().get();
    }

    public void setIsbn(Long isbn) {
        this.isbnProperty().setValue(isbn);
    }

    public String getGivenName() {
        return givenNameProperty().get();
    }

    public void setGivenName(String givenName) {
        this.givenNameProperty().setValue(givenName);
    }

    public String getFamilyName() {
        return familyNameProperty().get();
    }

    public void setFamilyName(String familyName) {
        this.familyNameProperty().setValue(familyName);
    }

    public String getNickname() {
        return nicknameProperty().get();
    }

    public void setNickname(String nickname) {
        this.nicknameProperty().set(nickname);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LongProperty isbnProperty() {
        return isbn;
    }

    public StringProperty givenNameProperty() {
        return givenName;
    }

    public StringProperty familyNameProperty() {
        return familyName;
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

}
