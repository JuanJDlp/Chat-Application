package com.arkjj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginWindow"));
        stage.setTitle("Login");
        stage.setScene(scene);
        Image image = new Image(
                "C:\\Users\\juanj\\Desktop\\Universidad Icesi\\Programing\\Java\\Chat application\\Client JavaFX\\clientfx\\src\\main\\resources\\com\\arkjj\\img\\pngaaa.com-5160457.png");
        stage.getIcons().add(image);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}