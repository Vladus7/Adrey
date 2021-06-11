package com.exeption;

public class UserWasNotChanged extends Exception{

    public UserWasNotChanged() {
    }

    public UserWasNotChanged(String message) {
        super(message);
    }
}
