package com.ArkJJ.ChatApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Object content;
    private String senderID;
    private String receiverID;
    private String senderUsername;
    private Type type;

    public enum Type {
        JOIN,
        LEAVE,
        CHAT
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Message(Object content) {
        this.content = content;
    }
}
