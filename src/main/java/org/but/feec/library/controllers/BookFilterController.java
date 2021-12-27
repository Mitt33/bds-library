package org.but.feec.library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.library.api.BookFilterView;
import org.but.feec.library.api.BookBasicView;
import org.but.feec.library.api.BookDetailView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.services.BookService;


import java.util.List;

public class BookFilterController {
    @FXML
    public TableColumn<BookFilterView, Long> personsId;
    @FXML
    public TableColumn<BookFilterView, Long> isbn;
    @FXML
    public TableColumn<BookFilterView, String> bookTitle;
    @FXML
    public TableColumn<BookFilterView, String> authorName;
    @FXML
    public TableColumn<BookFilterView, String> authorSurname;
    @FXML
    public TableView<BookFilterView> systemPersonsTableView;

    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private BookService bookService;
    private BookRepository bookRepository;
    @FXML
    private void initialize(){
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        personsId.setCellValueFactory(new PropertyValueFactory<BookFilterView, Long>("id"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<BookFilterView, String>("bookTitle"));
        isbn.setCellValueFactory(new PropertyValueFactory<BookFilterView, Long>("isbn"));
        authorName.setCellValueFactory(new PropertyValueFactory<BookFilterView, String>("authorName"));
        authorSurname.setCellValueFactory(new PropertyValueFactory<BookFilterView, String>("authorSurname"));


        ObservableList<BookFilterView> observablePersonList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonList);

//        LoadPersonData();
    }
    private ObservableList<BookFilterView> initializePersonsData() {

        String text = (String) stage.getUserData();
        System.out.println("controller "+text);
        List<BookFilterView> persons = bookService.getBookFilterView(text);
        return FXCollections.observableArrayList(persons);
    }
//    private void LoadPersonData(){
//        Stage stage =this.stage;
//        if (stage.getUserData() instanceof BookFilterView) {
//            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
//            idTextField.setText(String.valueOf(personBasicView.getId()));
//            isbnTextField.setText(String.valueOf(personBasicView.getIsbn()));
//            bookTitleTextField.setText(String.valueOf((personBasicView.getBookTitle())));
//            authorNameTextField.setText(personBasicView.getAuthorName());
//            authorSurnameTextField.setText(personBasicView.getAuthorSurname());
//            categoryNameTextField.setText(personBasicView.getCategoryName());
//            publishingHouseTextField.setText(personBasicView.getPublishingHouse());
//            datePublishedTextField.setText(personBasicView.getDatePublished());
//
//        }

//    }

}

