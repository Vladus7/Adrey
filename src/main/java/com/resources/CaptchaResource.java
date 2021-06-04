package com.resources;

import com.github.cage.GCage;
import com.service.RandomStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Component
@Path("/captcha")
public class CaptchaResource {
    private GCage captchaGenerator = new GCage();
    @Autowired
    private RandomStringService randomStringService;

    @GET
    @Path("/")
    @Produces({"image/png"})
    public Response getCaptcha(@Context HttpServletRequest request) {
        String captcha = randomStringService.get(4);
        request.getSession().setAttribute("captcha", captcha);
        return Response.ok().entity((StreamingOutput) output -> {
            captchaGenerator.draw(captcha, output);
            output.flush();
        }).build();
    }
}