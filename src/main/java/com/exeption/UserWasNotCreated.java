package com.exeption;

public class UserWasNotCreated extends Exception{

    public UserWasNotCreated() {
    }

    public UserWasNotCreated(String message) {
        super(message);
    }
}
