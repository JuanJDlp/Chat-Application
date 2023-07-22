package com.arkjj.Client;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;
import com.arkjj.model.User;
import com.google.gson.Gson;

import javafx.application.Platform;

public class MyStompSessionHandler implements StompSessionHandler {

    private MessagePojo messagePOJO;
    private ChatWindowController windowController;
    private StompSession GlobalSession;
    private static Gson gson = new Gson();

    public MyStompSessionHandler(ChatWindowController windowController) {
        this.windowController = windowController;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessagePojo.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Platform.runLater(() -> {
            messagePOJO = (MessagePojo) payload;
            System.out
                    .println(
                            "Got a new message: " + messagePOJO.getContent() + "\n from: " + messagePOJO.getSenderID());
            if (messagePOJO.getType().equals(MessagePojo.Type.JOIN) && !messagePOJO.getSenderUsername().equals("")) {
                windowController.displayUserConnected(messagePOJO);
                windowController.updateConnectedUsers(getConnectedUsers());
                return;
            }

            if (messagePOJO.getContent() != null && !messagePOJO.getSenderID().equals(GlobalSession.getSessionId())
                    && messagePOJO.getType().equals(MessagePojo.Type.CHAT)) {
                windowController.receiveMessage((String) messagePOJO.getContent());
            }
        });

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connected succesfully");
        System.out.println("You session ID is: " + session.getSessionId());
        this.GlobalSession = session;

        session.subscribe("/topic/public", this);
        session.subscribe("/user/" + session.getSessionId() + "/private", this);
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

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        System.out.println("Got an exception while handling a frame.\n" +
                "\nCommand: " + command +
                "\nHeaders: " + headers +
                "\nPayload: " + payload +
                "\n" + exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("The server is closed. ");
        System.exit(-1);
    }

}
