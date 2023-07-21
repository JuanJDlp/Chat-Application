package com.arkjj.POJOs;

public class MessagePojo {
    private String content;
    private String sender;
    private String[] receivers;

    public MessagePojo() {
    }

    public MessagePojo(String content) {
        this.content = content;
    }

    public MessagePojo(String content, String sender, String[] receivers) {
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
