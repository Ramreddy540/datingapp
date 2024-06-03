package com.example.otpverification;

public class ChatMessage {
    private String message;
    private int profilePic;
    private String time;
    private boolean isSentByMe;

    public ChatMessage(String message, int profilePic, String time, boolean isSentByMe) {
        this.message = message;
        this.profilePic = profilePic;
        this.time = time;
        this.isSentByMe = isSentByMe;
    }

    public String getMessage() {
        return message;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getTime() {
        return time;
    }

    public boolean isSentByMe() {
        return isSentByMe;
    }
}
