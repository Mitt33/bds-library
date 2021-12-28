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
    public Button filterButton;
    @FXML
    public TextField searchBar;
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
        loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit Book");
        MenuItem detailedView = new MenuItem("Detailed book view");
        MenuItem delete = new MenuItem("Delete book");
        edit.setOnAction((ActionEvent event) -> {
            BookBasicView bookView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/BookEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(bookView);
                stage.setTitle("BDS JavaFX Edit Book");

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
                BookDetailView bookDetailView = bookService.getBookDetailView(personId);

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

        delete.setOnAction((ActionEvent event) -> {
            BookBasicView bookBasicView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            Long bookId = bookBasicView.getId();
            bookRepository.removeBook(bookId);
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        menu.getItems().add(delete);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<BookBasicView> initializeBookData() {
        List<BookBasicView> books = bookService.getBookBasicView();
        return FXCollections.observableArrayList(books);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("images/icon.png"));
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
            stage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
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
    public void handleFilterButton(ActionEvent actionEvent){
        try {
            String text = searchBar.getText();
            System.out.println("handler" +text);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/BookFilter.fxml"));
            Stage stage = new Stage();
            BookFilterController bookFilterController = new BookFilterController();
            stage.setUserData(text);
            bookFilterController.setStage(stage);
            fxmlLoader.setController(bookFilterController);
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);


            stage.setTitle("Book filtered view");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }

    }
        public void handleInjectionButton (ActionEvent actionEvent){
            try {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/injectionTraining.fxml"));
                Stage stage = new Stage();
                InjectionController injectionController = new InjectionController();

                injectionController.setStage(stage);
                fxmlLoader.setController(injectionController);
                Scene scene = new Scene(fxmlLoader.load(), 600, 500);


                stage.setTitle("SQL Injection training");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        }

}
