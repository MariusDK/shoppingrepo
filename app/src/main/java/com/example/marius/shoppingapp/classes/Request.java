package com.example.marius.shoppingapp.classes;

public class Request {

    private String sender;
    private String reciver;
    private String status;

    public Request() {
    }

    public Request(String sender, String reciver, String status) {
        this.sender = sender;
        this.reciver = reciver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
