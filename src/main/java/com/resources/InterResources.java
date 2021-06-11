package com.resources;


import com.filter.JwtProvider;
import com.model.LoginUserDto;
import com.model.RegistrationUserDto;
import com.model.Role;
import com.model.User;
import com.service.CaptchaService;
import com.service.RoleService;
import com.service.UserService;
import com.validator.LoginValidator;
import com.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

@Component
@Path("/inter")
public class InterResources {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegistrationValidator userRegistrationValidator;
    @Autowired
    private LoginValidator loginValidator;
    @Autowired
    private JwtProvider jwtProvider;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserDto userDto) throws URISyntaxException {
        Map<String, String> errors = loginValidator.validate(userDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(errors).build();
        }
        return Response.ok().header("Authorization", jwtProvider.generateToken(userDto.getLogin())).build();
    }

    @POST
    @Path("registration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registration(RegistrationUserDto registrationUserDto, @Context HttpServletRequest request) {
        Map<String, String> errors = userRegistrationValidator.validate(registrationUserDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(errors).build();
        }
        userService.create(registrationUserDto.toUser(roleService));
        return Response.ok().header("Authorization", jwtProvider.generateToken(registrationUserDto.getLogin())).build();
    }
}
