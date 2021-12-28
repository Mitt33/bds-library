package org.but.feec.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.but.feec.library.exceptions.ExceptionHandler;

/**
 * @author Martin Svoboda
 */
public class App extends Application {

    private FXMLLoader loader;
    private VBox mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            mainStage = loader.load();


            primaryStage.setTitle("BDS JavaFX Library");
            Scene scene = new Scene(mainStage);
//            setUserAgentStylesheet(STYLESHEET_MODENA);
//            String myStyle = getClass().getResource("css/myStyle.css").toExternalForm();
//            scene.getStylesheets().add(myStyle);


            primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
    }

}
