package com.nhatton.simplemess;

/**
 * Created by Norvia on 23/02/2017.
 */

public class ChatMessage {
    private String mEmail;
    private String mMessage;
    private String mUid;

    public ChatMessage(){

    }

    public ChatMessage(String email, String message, String uid){
        mEmail = email;
        mMessage = message;
        mUid = uid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}
