package com.arkjj;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.arkjj.Client.WebSocketClientImp;
import com.arkjj.model.MessagePojo;
import com.arkjj.model.User;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> listViewContacts;

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
        listViewContacts.getItems().add(titleLabel.getText()); // This line adds chat room to the possible contacts to
                                                               // text
        listViewContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // TODO: OPEN A COMUNICATION WITH THAT CHANNEL
            titleLabel.setText(newValue);
        });

        vboxMessages.heightProperty().addListener((observable, oldValue, newValue) -> {
            spMain.setVvalue((Double) newValue);

        });

        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().toString().equals("ENTER")) {
                    send();
                }
            }

        });

    }

    public void updateConnectedUsers(List<User> newUsers) {
        // TODO: FIX THIS SO WHEN A USER CONNECTS IT DOES NOT RESET THE CHAT
        String nameOfTheMainRoom = "Chat room";
        listViewContacts.getItems().clear();
        listViewContacts.getItems().add(nameOfTheMainRoom);
        titleLabel.setText(nameOfTheMainRoom);
        for (User user : newUsers) {
            if (!user.getUsername().equals(username)) {
                listViewContacts.getItems().add(user.getUsername());
            }
        }
    }

    public void startConnection() {
        client = new WebSocketClientImp(this, username);
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

    public void displayUserConnected(MessagePojo messagePojo) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text((String) messagePojo.getContent());
        text.setFill(javafx.scene.paint.Color.WHITE);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(15,125,245); " +
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