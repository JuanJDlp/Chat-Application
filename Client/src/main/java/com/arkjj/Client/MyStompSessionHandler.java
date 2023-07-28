package com.arkjj.Client;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.arkjj.POJOs.Message;

public class MyStompSessionHandler implements StompSessionHandler {

    private Message messagePOJO;

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("Got a new message: " + payload);
        messagePOJO = (Message) payload;
        System.out.println(messagePOJO.getContent());
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connected succesfully");
        session.subscribe("/topic/greetings", this);
        session.send("/app/hello", new Message("Hello from the client"));
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTransportError'");
    }

}
