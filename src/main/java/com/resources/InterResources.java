package com.resources;

import com.model.LoginUserDto;
import com.model.RegistrationUserDto;
import com.model.User;
import com.service.RoleService;
import com.service.UserService;
import com.validator.LoginValidator;
import com.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserDto userDto) throws URISyntaxException {
        Map<String, String> errors = loginValidator.validate(userDto);
        System.out.println(errors);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity(errors).build();
        }
       // autoLogin(userService.findByLogin(userDto.getLogin()));
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(RegistrationUserDto registrationUserDto, @Context HttpServletRequest request) {
        Map<String, String> errors = userRegistrationValidator.validate(registrationUserDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity(errors).build();
        }
        userService.create(registrationUserDto.toUser(roleService));
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    private void autoLogin(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        user.getLogin(), user.getPassword(), authorities));
    }
}
