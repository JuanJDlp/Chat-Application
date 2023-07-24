package com.ArkJJ.ChatApp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ArkJJ.ChatApp.model.Message;
import com.ArkJJ.ChatApp.model.User;
import com.google.gson.Gson;

@Controller
public class ChatController {

    @Autowired
    @Qualifier("users")
    public List<User> users;

    private static Gson gson = new Gson();

    private SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("A user sent a message " + chatMessage.getSenderID());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        User newUser = gson.fromJson((String) chatMessage.getContent(), User.class);

        users.add(newUser);

        System.out.println("A user joined " + newUser.getUsername());
        headerAccessor.getSessionAttributes().put("idSesion", newUser.getId());
        headerAccessor.getSessionAttributes().put("username", newUser.getUsername());

        return setUpNMessageForLogin(chatMessage, newUser);
    }

    private Message setUpNMessageForLogin(Message chatMessage, User newUser) {
        chatMessage.setContent("A new user has joined! " + newUser.getUsername());
        chatMessage.setType(Message.Type.JOIN);
        chatMessage.setSenderUsername(newUser.getUsername());
        return chatMessage;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        message.setSenderUsername(message.getSenderUsername());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverID(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}
