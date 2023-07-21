package com.ArkJJ.ChatApp.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ArkJJ.ChatApp.model.Message;

@Controller
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("A user sent a message " + chatMessage.getSenderID());
        chatMessage.setType(Message.Type.CHAT);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderID());

        System.out.println("A user joined " + headerAccessor.getSessionId());

        chatMessage.setContent("A new user has joined! " + chatMessage.getSenderUsername());
        chatMessage.setType(Message.Type.JOIN);
        chatMessage.setSenderUsername(chatMessage.getSenderUsername());
        return chatMessage;
    }

    @MessageMapping("/private-message")
    private void privateMessage(@Payload Message message) {
        System.out
                .println("A private message was sent from " + message.getSenderID() + " to " + message.getReceiverID());

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverID(), "/private", message);

    }
}
