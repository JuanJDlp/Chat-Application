package com.ArkJJ.ChatApp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ArkJJ.ChatApp.model.Message;
import com.ArkJJ.ChatApp.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketEventListener {

    @Autowired
    @Qualifier("users")
    public List<User> users;

    // @Autowired
    // private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            users.removeIf(user -> user.getUsername().equals(username));
        }
    }
}
