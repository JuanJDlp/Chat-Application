package com.arkjj.model;

public class MessagePojo {
    private String content;
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

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverID() {
        return receiverID;

    }

    public MessagePojo() {
    }

    public MessagePojo(String content) {
        this.content = content;
    }

    public MessagePojo(String content, String SenderID, String senderUsername) {
        this.content = content;
        this.senderID = SenderID;
        this.senderUsername = senderUsername;
    }

    public String getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderID(String SenderID) {
        this.senderID = SenderID;
    }
}
