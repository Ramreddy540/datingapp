package com.example.otpverification;

public class Member {
    private String name;
    private int photo;
    private String lastMessage;
    private String time;
    private int unreadMessages;

    public Member(String name, int photo, String lastMessage, String time, int unreadMessages) {
        this.name = name;
        this.photo = photo;
        this.lastMessage = lastMessage;
        this.time = time;
        this.unreadMessages = unreadMessages;
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

    public int getUnreadMessages() {
        return unreadMessages;
    }
}
