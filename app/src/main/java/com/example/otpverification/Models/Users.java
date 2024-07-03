package com.example.otpverification.Models;

public class Users {

    String  firstName, dob, Height, Religion, Status, Language, Smoke, Community, Drinking,
            imageUrl, phoneNum;

    public Users(String firstName, String dob, String height, String religion, String status,
                 String language, String smoke, String community, String drinking, String imageUrl,
                 String phone_Num) {
        this.firstName = firstName;
        this.dob = dob;
        Height = height;
        Religion = religion;
        Status = status;
        Language = language;
        Smoke = smoke;
        Community = community;
        Drinking = drinking;
        this.imageUrl = imageUrl;
        this.phoneNum = phone_Num;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getSmoke() {
        return Smoke;
    }

    public void setSmoke(String smoke) {
        Smoke = smoke;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getDrinking() {
        return Drinking;
    }

    public void setDrinking(String drinking) {
        Drinking = drinking;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setPhone_Num(String phone_Num) {
        this.phoneNum = phone_Num;
    }

    public String getPhone_Num() {
        return phoneNum;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
