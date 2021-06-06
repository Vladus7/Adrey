package com.resources;

import com.exeption.CaptchaWasNotCreated;
import com.github.cage.GCage;
import com.mysql.jdbc.ParameterBindings;
import com.service.RandomStringService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Path("/captcha")
public class CaptchaResource {
    private GCage captchaGenerator = new GCage();
    private static final Logger LOGGER = Logger.getLogger(CaptchaResource.class);
    @Autowired
    private RandomStringService randomStringService;

    @GET
    @Path("/")
    @Produces({"image/png"})
    public Response getCaptcha(@Context HttpServletRequest request) {
        String captcha = randomStringService.get(4);
        request.getSession().setAttribute("captcha", captcha);
        return Response.ok()
                .header("captcha", captcha)
                .header("captcha256", bytesToHex(captcha))
                .entity((StreamingOutput) output -> {
            captchaGenerator.draw(captcha, output);
            output.flush();
        }).build();
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