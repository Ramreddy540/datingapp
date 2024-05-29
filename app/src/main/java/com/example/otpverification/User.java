package com.example.otpverification;

import java.util.List;

public class User {
    private String height;
    private String religion;
    private String community;
    private String smoke;
    private String status;
    private String drinking;
    private String language;
    private List<String> imageUrls;

    public User(String height, String religion, String community, String smoke, String status, String drinking, String language, List<String> imageUrls) {
        this.height = height;
        this.religion = religion;
        this.community = community;
        this.smoke = smoke;
        this.status = status;
        this.drinking = drinking;
        this.language = language;
        this.imageUrls = imageUrls;
    }

    public String getHeight() {
        return height;
    }

    public String getReligion() {
        return religion;
    }

    public String getCommunity() {
        return community;
    }

    public String getSmoke() {
        return smoke;
    }

    public String getStatus() {
        return status;
    }

    public String getDrinking() {
        return drinking;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
