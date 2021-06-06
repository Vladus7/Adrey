package com.resources;

import java.net.URISyntaxException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.model.EditUserDto;
import com.model.UserDto;
import com.service.ConvertorUsersToUserRowBeans;
import com.service.RoleService;
import com.service.UserService;
import com.validator.UserAddValidator;
import com.validator.UserEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.User;

@Component
@Path("/users")
public class UserResources {
    @Autowired
    private ConvertorUsersToUserRowBeans convertorUsersToUserRowBeans;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAddValidator userAddValidator;
    @Autowired
    private UserEditValidator userEditValidator;

    // http://localhost:8080/users/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok()
                .entity(convertorUsersToUserRowBeans.convert(userService.findAll()))
                .build();
    }

    // http://localhost:8080/users/1
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") long userId) {
        return Response.ok()
                .entity(new UserDto(userService.findById(userId))).build();
        //return new UserDto(userService.findById(userId));
    }

    // http://localhost:8080/users/add
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(EditUserDto userDto) {
        Map<String,String> errors = userAddValidator.validate(userDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity(errors).build();
        }
        userService.create(userDto.toUser(roleService));
        return Response.ok().build();
    }

    // http://localhost:8080/users/update
    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(EditUserDto userDto) {
        Map<String,String> errors = userEditValidator.validate(userDto);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity(errors).build();
        }
        userService.update(userDto.toUser(roleService));
        return Response.ok().build();
    }

    // http://localhost:8080/users/1
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) throws Exception {
        User user = userService.findById(id);
        if (Objects.isNull(user)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        userService.remove(user);
        return Response.ok(user).build();
    }
}