package com.resources;

import com.model.EditUserDto;
import com.model.LoginUserDto;
import com.model.RegistrationUserDto;
import com.model.User;
import com.service.RoleService;
import com.service.UserService;
import com.validator.LoginValidator;
import com.validator.UserRegistrationValidator;
//import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Path("/inter")
//@CrossOriginResourceSharing(
//        allowOrigins = {
//                "http://area51.mil:31415"
//        },
//        allowCredentials = true,
//        maxAge = 1,
//        allowHeaders = {
//                "X-custom-1", "X-custom-2"
//        },
//        exposeHeaders = {
//                "X-custom-3", "X-custom-4"
//        }
//)
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
        System.out.println("asdfadfadfsafdasdf");
        Map<String,String> errors = loginValidator.validate(userDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity(errors).build();
        }
        autoLogin(userService.findByLogin(userDto.getLogin()));
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("registration")
//    @CrossOrigin(origins = "*", methods = {RequestMethod.POST})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(RegistrationUserDto registrationUserDto, @Context HttpServletRequest request) {
        try {
            System.out.println("<Regisraция>");
            Map<String, String> errors = userRegistrationValidator.validate(registrationUserDto);
            if (!errors.isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity(errors).build();
            }
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }






//    @POST
//    @Path("registration")
////    @CrossOrigin(origins = "*", methods = {RequestMethod.POST})
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response registration(RegistrationUserDto registrationUserDto, @Context HttpServletRequest request) {
//        try {
//            System.out.println("<Regisraция>");
//            Map<String, String> errors = userRegistrationValidator.validate(registrationUserDto);
//            if (!errors.isEmpty()) {
//                return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity(errors).build();
//            }
//            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }





//    @OPTIONS
//    @Path("registration")
//    @CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS})
//    public Response registrationOptions() {
//        return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
//    }

//    @POST
//    @Path("registration")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response registration(RegistrationUserDto registrationUserDto, @Context HttpServletRequest request) {
//        System.out.println("<Regisraция>");
//        Map<String, String> errors = userRegistrationValidator.validate(registrationUserDto);
//        if (!errors.isEmpty()) {
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .header("Access-Control-Allow-Origin", "*")
//                    .header("Access-Control-Allow-Headers", "CSRF-Token, X-Requested-By, Authorization, Content-Type")
//            .header("Access-Control-Allow-Credentials", "true")
//           .header("Access-Control-Allow-Methods",
//                    "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(errors).build();
//        }
//        return Response.ok().header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Headers", "CSRF-Token, X-Requested-By, Authorization, Content-Type")
//                .header("Access-Control-Allow-Credentials", "true")
//                .header("Access-Control-Allow-Methods",
//                        "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
//    }

    private void autoLogin(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        user.getLogin(), user.getPassword(), authorities));
    }

}
