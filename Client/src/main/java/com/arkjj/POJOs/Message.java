package com.arkjj.POJOs;

public class Message {
    private String content;
    private String sender;
    private String[] receivers;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(String content, String sender, String[] receivers) {
        this.content = content;
        this.sender = sender;
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }
}
