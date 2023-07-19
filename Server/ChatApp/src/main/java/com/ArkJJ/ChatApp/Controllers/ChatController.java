package com.ArkJJ.ChatApp.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ArkJJ.ChatApp.POJOs.Message;

@Controller
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message messageHandler(Message message) {
        System.out.println(message.getSender() + " : " + message.getContent());
        return new Message(message.getContent());
    }

}
