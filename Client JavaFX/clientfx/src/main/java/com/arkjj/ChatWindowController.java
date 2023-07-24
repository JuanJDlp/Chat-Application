package com.arkjj;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.arkjj.Client.WebSocketClientImp;
import com.arkjj.model.MessagePojo;
import com.arkjj.model.User;
import com.google.gson.Gson;

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

import java.util.Arrays;
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

    private WebSocketClientImp client;

    private String username;

    private static Gson gson = new Gson();

    // TODO: REFACTOR THIS
    private String channel = "/app/chat.sendMessage";
    private String receiver = null;

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
            titleLabel.setText(newValue);
            List<User> newUsers = getConnectedUsers();
            channel = "/app/chat.sendMessage";
            receiver = null;
            for (User user : newUsers) {
                if (user.getUsername().equals(newValue)) {
                    receiver = user.getId();
                    channel = "/app/private-message";
                    break;
                }
            }
            Optional<User> user = newUsers.stream().filter(userFiltered -> userFiltered.getUsername().equals(newValue))
                    .findFirst();
            if (user.isPresent()) {
                User userFiltered = user.get();
                vboxMessages.getChildren().clear();
                vboxMessages.getChildren().addAll(messagesMap.get(userFiltered.getId()));
            } else {
                vboxMessages.getChildren().clear();

                vboxMessages.getChildren().addAll(chatRoomMessages);
            }
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

    private List<User> getConnectedUsers() {
        String apiURL = "http://localhost:8080/sessions/findAll";
        List<User> userList = null;
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(apiURL))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> response = httpClient.send(getRequest,
                    HttpResponse.BodyHandlers.ofString());

            User[] userArray = gson.fromJson(response.body(), User[].class);
            // Convert the array to a List if needed
            userList = Arrays.asList(userArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void updateConnectedUsers() {
        // TODO: FIX THIS SO WHEN A USER CONNECTS IT DOES NOT RESET THE CHAT
        List<User> newUsers = getConnectedUsers();
        String nameOfTheMainRoom = "Chat room";
        listViewContacts.getItems().clear();
        listViewContacts.getItems().add(nameOfTheMainRoom);
        titleLabel.setText(nameOfTheMainRoom);

        for (User user : newUsers) {
            if (!user.getUsername().equals(username)) {
                listViewContacts.getItems().add(user.getUsername());
                if (!messagesMap.containsKey(user.id)) {
                    messagesMap.put(user.id, new VBox());
                }
            }
        }
    }

    public void startConnection() {
        client = new WebSocketClientImp(this, username);
    }

    public void receiveMessage(MessagePojo messagePojo) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 10, 5, 5));
        Text text = new Text((String) messagePojo.getContent());
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(238,238,228); " +
                        "-fx-color: rgb(255,255,255); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 5px");
        hbox.getChildren().add(textFlow);
        chatRoomMessages.getChildren().add(hbox);

        if (titleLabel.getText().equals("Chat room")) {
            System.out.println("Eestas en la chat room");
            vboxMessages.getChildren().setAll(chatRoomMessages);
        }
    }

    public void receivePrivateMessage(MessagePojo messagePojo) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 10, 5, 5));
        Text text = new Text((String) messagePojo.getContent());
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(238,238,228); " +
                        "-fx-color: rgb(255,255,255); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 5px");
        hbox.getChildren().add(textFlow);

        System.out.println("No estas en la chat room");
        VBox vbox = messagesMap.get(messagePojo.getSenderID());
        vbox.getChildren().add(hbox);
        if (messagePojo.getSenderUsername().equals(titleLabel.getText())) {
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(vbox);
        }
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
        chatRoomMessages.getChildren().add(hbox);
        vboxMessages.getChildren().setAll(chatRoomMessages);
    }

    public void displayUserDisconnected(MessagePojo messagePojo) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text((String) messagePojo.getContent());

        text.setFill(javafx.scene.paint.Color.BLACK);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: rgb(240, 34, 44); " +
                        "-fx-color: rgb(255,255,255); " +
                        "-fx-background-radius: 20px; " +
                        "-fx-padding: 5px");
        hbox.getChildren().add(textFlow);
        chatRoomMessages.getChildren().add(hbox);
        vboxMessages.getChildren().setAll(chatRoomMessages);
    }

    public void send() {
        String message = username + "\n" + textArea.getText();
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

        if (channel.equals("/app/chat.sendMessage")) {
            client.sendMessage(message);
            chatRoomMessages.getChildren().add(hbox);
            vboxMessages.getChildren().setAll(chatRoomMessages);
        } else {
            System.out.println("No estas en la chat room");
            VBox vbox = messagesMap.get(receiver);
            vbox.getChildren().add(hbox);
            vboxMessages.getChildren().clear();
            vboxMessages.getChildren().addAll(vbox);

            client.sendPrivateMessage(message, receiver, username);
        }

    }

}