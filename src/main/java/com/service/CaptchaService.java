package com.service;

import com.exeption.CaptchaWasNotCreated;
import com.github.cage.GCage;
import com.resources.CaptchaResource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service("captchaService")
public class CaptchaService {
    private GCage captchaGenerator = new GCage();
    private Map<String, String> captchaStorage = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(CaptchaService.class);
    @Autowired
    private RandomStringService randomStringService;

    public void write(String id, OutputStream output){
        createCaptcha(id);
        try {
            captchaGenerator.draw(captchaStorage.get(id), output);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
            throw new CaptchaWasNotCreated(e);
        }
    }

    public boolean validCaptcha(String id, String captcha){
        return captcha.equals(captchaStorage.get(id));
    }

    private void createCaptcha(String id){
        captchaStorage.put(id, randomStringService.get(4));
    }

    private static String bytesToHex(String captcha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(captcha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.log(Level.ERROR, noSuchAlgorithmException);
            throw new CaptchaWasNotCreated(noSuchAlgorithmException);
        }
    }
}
