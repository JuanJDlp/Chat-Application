package com.arkjj.Client;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;

import javafx.application.Platform;

public class MyStompSessionHandler implements StompSessionHandler {

    private MessagePojo messagePOJO;
    private ChatWindowController windowController;
    private StompSession GlobalSession;

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

            if (messagePOJO.getType().equals(MessagePojo.Type.JOIN)) {
                // TODO: IMPLEMENT THE UPDATING THE ADDED USERS
                windowController.displayUserConnected(messagePOJO);
                return;
            }

            if (messagePOJO.getContent() != null && !messagePOJO.getSenderID().equals(GlobalSession.getSessionId())
                    && messagePOJO.getType().equals(MessagePojo.Type.CHAT)) {
                windowController.receiveMessage(messagePOJO.getContent());
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
