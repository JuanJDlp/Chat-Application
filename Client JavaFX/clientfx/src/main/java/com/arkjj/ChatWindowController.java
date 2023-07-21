package com.arkjj;

import java.net.URL;
import java.util.ResourceBundle;

import com.arkjj.Client.WebSocketClientImp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatWindowController implements Initializable {

    @FXML
    private Button sendBTN;

    @FXML
    private TextField textArea;
    @FXML
    private ScrollPane spMain;

    @FXML
    private VBox vboxMessages;

    private WebSocketClientImp client;

    private String username;

    public ChatWindowController() {
    }

    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new WebSocketClientImp(this);
        System.out.println(username);
        vboxMessages.heightProperty().addListener((observable, oldValue, newValue) -> {
            spMain.setVvalue((Double) newValue);

        });

    }

    public void receiveMessage(String message) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 10, 5, 5));
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(238,238,228); " +
                        "-fx-color: rgb(255,255,255); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 5px");

        hbox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hbox);
    }

    public void send() {
        String message = textArea.getText();
        if (message.isEmpty()) {
            return;
        }
        textArea.clear();
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text(message);
        text.setFill(javafx.scene.paint.Color.WHITE);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(15,125,245); " +
                        "-fx-color: rgb(255,255,255); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 5px");

        hbox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hbox);
        client.sendMessage(message);

    }

}