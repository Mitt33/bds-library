package org.but.feec.library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
//import org.but.feec.library.api.BookFilterView;
import org.but.feec.library.api.InjectionView;
import org.but.feec.library.api.BookBasicView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.services.BookService;

import java.awt.print.Book;
import java.util.List;

public class InjectionController {
    @FXML
    private TableColumn<InjectionView, Long> id;
    @FXML
    private TableColumn<InjectionView, Long> age;
    @FXML
    private TableColumn<InjectionView, String> name;
    @FXML
    private TableColumn<InjectionView, String> surname;

    @FXML
    private TableView<InjectionView> systemPersonsTableView;
    @FXML
    private TextField inputField;

    private BookService bookService;
    private BookRepository bookRepository;

    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        id.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("surname"));
        age.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("age"));



    }
    private ObservableList<InjectionView> initializeBookData() {

        String input = inputField.getText();
        List<InjectionView> book = bookService.getInjectionView(input);
        return FXCollections.observableArrayList(book);
    }
    public void handleConfirmButton(ActionEvent actionEvent){
        ObservableList<InjectionView> observablePersonList = initializeBookData();
        systemPersonsTableView.setItems(observablePersonList);

    }


}

