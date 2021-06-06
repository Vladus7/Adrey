package com.exeption;

public class CaptchaWasNotCreated extends RuntimeException{

    public CaptchaWasNotCreated(Throwable cause) {
        super("Captcha wasn't created, because", cause);
    }
}
