package com.exeption;

public class UserWasNotDeleted extends Exception{

    public UserWasNotDeleted() {
    }

    public UserWasNotDeleted(String message) {
        super(message);
    }
}
