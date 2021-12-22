package org.but.feec.library.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.library.App;
import org.but.feec.library.api.BookBasicView;
import org.but.feec.library.api.BookDetailView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.but.feec.library.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @FXML
    public Button addBookButton;
    @FXML
    public Button refreshButton;
    @FXML
    private TableColumn<BookBasicView, Long> personsId;
    @FXML
    private TableColumn<BookBasicView, String> bookTitle;
    @FXML
    private TableColumn<BookBasicView, Long> isbn;
    @FXML
    private TableColumn<BookBasicView, String> authorName;
    @FXML
    private TableColumn<BookBasicView, String> authorSurname;
    @FXML
    private TableColumn<BookBasicView, String> publishingHouse;
    @FXML
    private TableView<BookBasicView> systemPersonsTableView;
    @FXML
    public MenuItem exitMenuItem;

    private BookService bookService;
    private BookRepository bookRepository;

    public BookController() {
    }

    @FXML
    private void initialize() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        personsId.setCellValueFactory(new PropertyValueFactory<BookBasicView, Long>("id"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<BookBasicView, String>("bookTitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<BookBasicView, Long>("isbn"));
        authorName.setCellValueFactory(new PropertyValueFactory<BookBasicView, String>("authorName"));
        authorSurname.setCellValueFactory(new PropertyValueFactory<BookBasicView, String>("authorSurname"));
        publishingHouse.setCellValueFactory(new PropertyValueFactory<BookBasicView, String>("publishingHouse"));



        ObservableList<BookBasicView> observableBookList = initializeBookData();
        systemPersonsTableView.setItems(observableBookList);

        systemPersonsTableView.getSortOrder().add(personsId);

        initializeTableViewSelection();
      //  loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit Book");
        MenuItem detailedView = new MenuItem("Detailed book view");
        edit.setOnAction((ActionEvent event) -> {
            BookBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("BDS JavaFX Edit Person");

                BookEditController controller = new BookEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            BookBasicView bookView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/BookDetailView.fxml"));
                Stage stage = new Stage();

                Long personId = bookView.getId();
                BookDetailView bookDetailView = bookService.getPersonDetailView(personId);

                stage.setUserData(bookDetailView);
                stage.setTitle("BDS JavaFX book Detailed View");

                BookDetailViewController controller = new BookDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<BookBasicView> initializeBookData() {
        List<BookBasicView> books = bookService.getPersonsBasicView();
        return FXCollections.observableArrayList(books);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddBookButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/BookCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Person");
            stage.setScene(scene);

//            Stage stageOld = (Stage) signInButton.getScene().getWindow();
//            stageOld.close();
//
//            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/vut.jpg")));
//            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<BookBasicView> observablePersonsList = initializeBookData();
        systemPersonsTableView.setItems(observablePersonsList);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }
}
