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
    public Button editPersonButton;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField givenNameTextField;
    @FXML
    private TextField familyNameTextField;
    @FXML
    private TextField nicknameTextField;

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
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The id must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(emailTextField, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(givenNameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(familyNameTextField, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(nicknameTextField, Validator.createEmptyValidator("The nickname must not be empty."));

        editPersonButton.disableProperty().bind(validation.invalidProperty());

        loadPersonsData();

        logger.info("PersonsEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof BookBasicView) {
            BookBasicView bookBasicView = (BookBasicView) stage.getUserData();
            idTextField.setText(String.valueOf(bookBasicView.getId()));
            // emailTextField.setText(bookBasicView.getEmail());
            givenNameTextField.setText(String.valueOf(bookBasicView.getIsbn()));
            familyNameTextField.setText(bookBasicView.getBookTitle());
        }
    }

    @FXML
    public void handleEditPersonButton(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        Long id = Long.valueOf(idTextField.getText());
        String email = emailTextField.getText();
        String firstName = givenNameTextField.getText();
        String lastName = familyNameTextField.getText();
        String nickname = nicknameTextField.getText();

        BookEditView bookEditview = new BookEditView();
        bookEditview.setId(id);
        bookEditview.setEmail(email);
        bookEditview.setGivenName(firstName);
        bookEditview.setFamilyName(lastName);
        bookEditview.setNickname(nickname);

        bookService.editBook(bookEditview);

        personEditedConfirmationDialog();
    }

    private void personEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Edited Confirmation");
        alert.setHeaderText("Your person was successfully edited.");

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
