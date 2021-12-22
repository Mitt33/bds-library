package org.but.feec.library.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.feec.library.api.BookDetailView;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BookDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(BookDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private TextField authorNameTextField;

    @FXML
    private TextField authorSurnameTextField;

    @FXML
    private TextField publishingHouseTextField;

    @FXML
    private TextField literatureCategoryTextField;

    @FXML
    private TextField libraryTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        isbnTextField.setEditable(false);
        bookTitleTextField.setEditable(false);
        authorNameTextField.setEditable(false);
        authorSurnameTextField.setEditable(false);
        publishingHouseTextField.setEditable(false);
        literatureCategoryTextField.setEditable(false);
        libraryTextField.setEditable(false);

        loadBookData();

        logger.info("BookDetailViewController initialized");
    }

    private void loadBookData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof BookDetailView) {
            BookDetailView bookBasicView = (BookDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(bookBasicView.getId()));
            isbnTextField.setText(String.valueOf(bookBasicView.getIsbn()));
            bookTitleTextField.setText(bookBasicView.getBookTitle());
            authorNameTextField.setText(bookBasicView.getAuthorName());
            authorSurnameTextField.setText(bookBasicView.getAuthorSurname());
            publishingHouseTextField.setText(bookBasicView.getPublishingHouse());
            literatureCategoryTextField.setText(bookBasicView.getLiteratureCategory());
            libraryTextField.setText(bookBasicView.getLibrary());
        }
    }

}

