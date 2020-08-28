package com.gtappdevelopers.ibmbot;

public class MessageModel {

    private String message;
    public  final int BOT_TYPE=1;
    public  final int USER_TYPE=2;

    public MessageModel(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
