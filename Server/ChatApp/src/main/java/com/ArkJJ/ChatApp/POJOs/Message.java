package com.ArkJJ.ChatApp.POJOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String content;
    private String sender;
    private String[] receivers;

    public Message(String content) {
        this.content = content;
    }
}
