package com.service;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service("randomStringService")
public class RandomStringService {

    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numbers = "0123456789";
    String alphaNumeric = upperAlphabet + upperAlphabet.toLowerCase(Locale.ROOT) + numbers;
    Random random = new Random();

    public String get(int length) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            string.append(randomChar);
        }
        return string.toString();
    }
}
