package com.example.otpverification;

public class Member {
    private String name;
    private int photo;
    private String lastMessage;
    private String time;
    private int messageCount;

    public Member(String name, int photo, String lastMessage, String time, int messageCount) {
        this.name = name;
        this.photo = photo;
        this.lastMessage = lastMessage;
        this.time = time;
        this.messageCount = messageCount;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public int getMessageCount() {
        return messageCount;
    }
}
