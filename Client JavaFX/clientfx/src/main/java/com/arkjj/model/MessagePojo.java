package com.arkjj.model;

public class MessagePojo {
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

    public MessagePojo(Object content) {
        this.content = content;
    }

    public MessagePojo(Object content, String SenderID, String senderUsername) {
        this.content = content;
        this.senderID = SenderID;
        this.senderUsername = senderUsername;
    }

    public Object getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setSenderID(String SenderID) {
        this.senderID = SenderID;
    }
}
