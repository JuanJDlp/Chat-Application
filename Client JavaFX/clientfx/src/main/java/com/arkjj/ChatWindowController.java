package com.arkjj;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.arkjj.Client.WebSocketClientImp;
import com.arkjj.Services.UserService;
import com.arkjj.model.HBoxBuilder;
import com.arkjj.model.MessagePojo;
import com.arkjj.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

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

    private Map<String, VBox> messagesMap = new HashMap<>();

    private static final VBox chatRoomMessages = new VBox();;

    private UserService userService = new UserService();

    private WebSocketClientImp client;

    public ChatWindowController() {
    }

    public void startConnection(String username) {
        this.client = new WebSocketClientImp(this, username);
        updateConnectedUsers(); // Get all the users that are currently connected to the server

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Keeps trak of the scrollPane so when the Vboc gets too big the SP will be
        // updated
        vboxMessages.heightProperty().addListener((observable, oldValue, newValue) -> {
            spMain.setVvalue((Double) newValue);

        });

        listViewContacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            titleLabel.setText(newValue); // Updates de title depending where and who you are texting to
            String userID = userService.getUserIDByName(newValue);
            updateChatBoxDependingOnUser(userID);
            client.setReceiverID(userID);

        });

        textArea.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                send();
            }
        });

    }

    private void updateChatBoxDependingOnUser(String userID) {

        if (userID != null) {
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(messagesMap.get(userID));
        } else {
            // If a user was not found it means you are in the chat room so just put those
            // messages
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(chatRoomMessages);
        }
    }

    public void updateConnectedUsers() {
        // TODO: FIX THIS SO WHEN A USER CONNECTS IT DOES NOT RESET THE CHAT
        String nameOfTheMainRoom = "Chat Room";
        List<User> newUsers = userService.getConnectedUsers();
        listViewContacts.getItems().clear();
        listViewContacts.getItems().add(nameOfTheMainRoom);

        for (User user : newUsers) {
            if (!user.getUsername().equals(client.getUsername())) {

                listViewContacts.getItems().add(user.getUsername());

                if (!messagesMap.containsKey(user.getId())) {
                    messagesMap.put(user.getId(), new VBox());
                }
            }
        }
    }

    public void receiveMessage(MessagePojo messagePojo) {

        HBox hbox = new HBoxBuilder()
                .alignment(Pos.CENTER_LEFT)
                .padding(new Insets(5, 10, 5, 5))
                .addText((String) messagePojo.getContent(),
                        "-fx-background-color: rgb(238,238,228); " +
                                "-fx-color: rgb(255,255,255); " +
                                "-fx-background-radius: 20px; " +
                                "-fx-padding: 5px")
                .build();

        chatRoomMessages.getChildren().add(hbox);

        if (titleLabel.getText().equals("Chat room")) {
            vboxMessages.getChildren().setAll(chatRoomMessages);
        }
    }

    public void receivePrivateMessage(MessagePojo messagePojo) {
        HBox hbox = new HBoxBuilder()
                .alignment(Pos.CENTER_LEFT)
                .padding(new Insets(5, 10, 5, 5))
                .addText((String) messagePojo.getContent(),
                        "-fx-background-color: rgb(238,238,228); " +
                                "-fx-color: rgb(255,255,255); " +
                                "-fx-background-radius: 20px; " +
                                "-fx-padding: 5px")
                .build();

        VBox vbox = messagesMap.get(messagePojo.getSenderID());
        vbox.getChildren().add(hbox);
        if (messagePojo.getSenderUsername().equals(titleLabel.getText())) {
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(vbox);
        }
    }

    public void displayUserConnected(MessagePojo messagePojo) {
        HBox hbox = new HBoxBuilder()
                .alignment(Pos.CENTER)
                .padding(new Insets(5, 5, 5, 10))
                .addText((String) messagePojo.getContent(),
                        "-fx-background-color: rgb(15,125,245); " +
                                "-fx-color: rgb(255,255,255); " +
                                "-fx-background-radius: 20px; " +
                                "-fx-padding: 5px")
                .build();

        chatRoomMessages.getChildren().add(hbox);
        vboxMessages.getChildren().setAll(chatRoomMessages);
    }

    public void displayUserDisconnected(MessagePojo messagePojo) {
        HBox hbox = new HBoxBuilder()
                .alignment(Pos.CENTER)
                .padding(new Insets(5, 5, 5, 10))
                .addText((String) messagePojo.getContent(),
                        "-fx-background-color: rgb(240, 34, 44); " +
                                "-fx-color: rgb(255,255,255); " +
                                "-fx-background-radius: 20px; " +
                                "-fx-padding: 5px",
                        Color.BLACK)
                .build();

        chatRoomMessages.getChildren().add(hbox);
        vboxMessages.getChildren().setAll(chatRoomMessages);
    }

    public void send() {
        String message = textArea.getText();
        if (message.isEmpty()) {
            return;
        }
        textArea.clear();

        HBox hbox = new HBoxBuilder()
                .alignment(Pos.CENTER_RIGHT)
                .padding(new Insets(5, 10, 5, 5))
                .addText(message,
                        "-fx-background-color: rgb(15,125,245); " +
                                "-fx-color: rgb(255,255,255); " +
                                "-fx-background-radius: 20px; " +
                                "-fx-padding: 5px",
                        Color.WHITE)
                .build();

        message = client.getUsername() + "\n " + message;

        if (titleLabel.getText().equals("Chat Room")) {
            client.sendMessage("/app/chat.sendMessage", message);
            chatRoomMessages.getChildren().add(hbox);
            vboxMessages.getChildren().setAll(chatRoomMessages);
        } else {
            VBox vbox = messagesMap.get(client.getReceiverID());
            vbox.getChildren().add(hbox);
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(vbox);

            client.sendPrivateMessage("/app/private-message", message);
        }

    }

}