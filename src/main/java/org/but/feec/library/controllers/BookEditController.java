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
import org.but.feec.library.api.BookBasicView;
import org.but.feec.library.api.BookEditView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.services.BookService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class BookEditController {

    private static final Logger logger = LoggerFactory.getLogger(BookEditController.class);

    @FXML
    public Button editBookButton;
    @FXML
    public TextField isbnTextField;
    @FXML
    private TextField bookTitleTextField;
    @FXML
    private TextField authorNameTextField;
    @FXML
    private TextField authorSurnameTextField;
    @FXML
    private TextField publishingHouseTextField;

    private BookService bookService;
    private BookRepository bookRepository;
    private ValidationSupport validation;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        validation = new ValidationSupport();
        validation.registerValidator(isbnTextField, Validator.createEmptyValidator("The id must not be empty."));
       // idTextField.setEditable(false);
        validation.registerValidator(bookTitleTextField, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(authorNameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(authorSurnameTextField, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(publishingHouseTextField, Validator.createEmptyValidator("The nickname must not be empty."));

        editBookButton.disableProperty().bind(validation.invalidProperty());

        loadBookData();

        logger.info("BookEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadBookData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof BookBasicView) {
            BookBasicView bookBasicView = (BookBasicView) stage.getUserData();
            isbnTextField.setText(String.valueOf(bookBasicView.getIsbn()));
            bookTitleTextField.setText(bookBasicView.getBookTitle());
            authorNameTextField.setText(bookBasicView.getAuthorName());
            authorSurnameTextField.setText(bookBasicView.getAuthorSurname());
            publishingHouseTextField.setText(bookBasicView.getPublishingHouse());
        }
    }

    @FXML
    public void handleEditBookButton(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Long isbn = Long.valueOf(isbnTextField.getText());
        String bookTitle = bookTitleTextField.getText();
        String authorName = authorNameTextField.getText();
        String authorSurname = authorSurnameTextField.getText();
        String publishingHouse = publishingHouseTextField.getText();

        BookEditView bookEditview = new BookEditView();
        bookEditview.setIsbn(isbn);
        bookEditview.setBookTitle(bookTitle);
        bookEditview.setAuthorName(authorName);
        bookEditview.setAuthorSurname(authorSurname);
        bookEditview.setPublishingHouse(publishingHouse);

        bookService.editBook(bookEditview);

        bookEditedConfirmationDialog();
    }

    private void bookEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Edited Confirmation");
        alert.setHeaderText("CHosen book was successfully edited.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
