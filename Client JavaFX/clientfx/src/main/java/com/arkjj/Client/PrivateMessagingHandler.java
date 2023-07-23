package com.arkjj.Client;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;

import javafx.application.Platform;

public class PrivateMessagingHandler extends MyStompSessionHandler {

    private MessagePojo messagePOJO;
    private ChatWindowController windowController;
    private StompSession GlobalSession;

    public PrivateMessagingHandler(ChatWindowController windowController) {
        super(windowController);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Platform.runLater(() -> {
            messagePOJO = (MessagePojo) payload;
            System.out
                    .println(
                            "Got a new message: " + messagePOJO.getContent() + "\n from: " + messagePOJO.getSenderID());

            if (messagePOJO.getContent() != null && !messagePOJO.getSenderID().equals(GlobalSession.getSessionId())
                    && messagePOJO.getType().equals(MessagePojo.Type.CHAT)) {
                windowController.receiveMessage(messagePOJO);
            }
        });

    }

}
