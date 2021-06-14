package com.resources;

import com.exeption.CaptchaWasNotCreated;
import com.github.cage.GCage;
import com.model.Role;
import com.model.User;
import com.mysql.jdbc.ParameterBindings;
import com.service.CaptchaService;
import com.service.RandomStringService;
import com.service.RoleService;
import com.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
@Path("/captcha")
public class CaptchaResource {
    private static final Logger LOGGER = Logger.getLogger(CaptchaResource.class);
    @Autowired
    private CaptchaService captchaService;

    @GET
    @Path("/{id}")
    @CrossOrigin(origins = "*")
    @Produces({"image/png"})
    public Response getCaptcha(@PathParam("id") String id) {
        return Response.ok()
                .entity((StreamingOutput) output -> {
            captchaService.write(id, output);
            output.flush();
        }).build();
    }
}