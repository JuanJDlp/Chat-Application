package com.arkjj.POJOs;

public class MessagePojo {
    private String content;
    private String sender;

    public MessagePojo() {
    }

    public MessagePojo(String content) {
        this.content = content;
    }

    public MessagePojo(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
