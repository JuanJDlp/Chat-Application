package com.arkjj.Client;

import org.springframework.messaging.simp.stomp.StompHeaders;

import com.arkjj.ChatWindowController;
import com.arkjj.model.MessagePojo;

import javafx.application.Platform;

public class PrivateMessagingHandler extends MyStompSessionHandler {

    private MessagePojo messagePOJO;
    private ChatWindowController windowController;

    public PrivateMessagingHandler(ChatWindowController windowController) {
        super(windowController);
        this.windowController = windowController;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Platform.runLater(() -> {
            messagePOJO = (MessagePojo) payload;
            System.out
                    .println(
                            "Got a new message: " + messagePOJO.getContent() + "\n from: " + messagePOJO.getSenderID());

            if (messagePOJO.getContent() != null && messagePOJO.getType().equals(MessagePojo.Type.CHAT)) {
                windowController.receivePrivateMessage(messagePOJO);
            }
        });

    }

}
