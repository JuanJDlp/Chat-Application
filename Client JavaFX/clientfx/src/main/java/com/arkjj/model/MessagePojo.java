package com.arkjj.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessagePojo {
    private Object content;
    private String senderID;
    private String receiverID;
    private String senderUsername;
    private Type type;

    @Getter
    public enum Type {
        JOIN,
        LEAVE,
        CHAT
    }

    public MessagePojo(Object content) {
        this.content = content;
    }

    public MessagePojo(Object content, String SenderID, String senderUsername) {
        this.content = content;
        this.senderID = SenderID;
        this.senderUsername = senderUsername;
    }

}
