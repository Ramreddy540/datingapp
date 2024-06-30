package com.example.otpverification;


public class YourLikeItem {

    private String imageResourceId;
    private String text;

    public YourLikeItem(String imageResourceId, String text) {
        this.imageResourceId = imageResourceId;
        this.text = text;
    }

    public String getImageResourceId() {

        return imageResourceId;
    }

    public String getText() {

        return text;
    }

}
