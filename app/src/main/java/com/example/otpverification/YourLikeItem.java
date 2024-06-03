package com.example.otpverification;
public class YourLikeItem {
    private int imageResourceId;
    private String text;

    public YourLikeItem(int imageResourceId, String text) {
        this.imageResourceId = imageResourceId;
        this.text = text;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getText() {
        return text;
    }
}
