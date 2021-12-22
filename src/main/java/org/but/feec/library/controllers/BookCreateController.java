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
import javafx.util.Duration;
import org.but.feec.library.api.BookCreateView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.services.BookService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BookCreateController {

    private static final Logger logger = LoggerFactory.getLogger(BookCreateController.class);

    @FXML
    public Button newBookCreateBook;
    @FXML
    private TextField newBookIsbn;

    @FXML
    private TextField newBookTitle;

    @FXML
    private TextField newBookAuthorName;

    @FXML
    private TextField newBookAuthorSurname;

    @FXML
    private TextField newBookPublishingHouse;

    private BookService bookService;
    private BookRepository bookRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newBookIsbn, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(newBookTitle, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newBookAuthorName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newBookAuthorSurname, Validator.createEmptyValidator("The nickname must not be empty."));
        validation.registerValidator(newBookPublishingHouse, Validator.createEmptyValidator("The password must not be empty."));

        newBookCreateBook.disableProperty().bind(validation.invalidProperty());

        logger.info("BookCreateController initialized");
    }

    @FXML
    void handleCreateNewBook(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Long isbn = Long.valueOf(newBookIsbn.getText());
        String bookTitle = newBookTitle.getText();
        String authorName = newBookAuthorName.getText();
        String authorSurname = newBookAuthorSurname.getText();
        String publishingHouse = newBookPublishingHouse.getText();

        BookCreateView bookCreateView = new BookCreateView();
        bookCreateView.setIsbn(isbn);
        bookCreateView.setBookTitle(bookTitle);
        bookCreateView.setAuthorName(authorName);
        bookCreateView.setAuthorSurname(authorSurname);
        bookCreateView.setPublishingHouse(publishingHouse);

        bookService.createBook(bookCreateView);

        bookCreatedConfirmationDialog();
    }

    private void bookCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Created Confirmation");
        alert.setHeaderText("Your book was successfully created.");

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