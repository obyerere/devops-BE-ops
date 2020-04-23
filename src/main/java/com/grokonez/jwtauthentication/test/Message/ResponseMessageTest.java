package com.grokonez.jwtauthentication.test.Message;

public class ResponseMessageTest {

       private String message;

    public ResponseMessageTest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
