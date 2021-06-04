package com.resources;

import java.net.URISyntaxException;
import java.net.URI;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.model.EditUserDto;
import com.service.ConvertorUsersToUserRowBeans;
import com.service.RoleService;
import com.service.UserService;
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
    public User getUser(@PathParam("id") long userId) {
        return userService.findById(userId);
    }

    // http://localhost:8080/users/add
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(EditUserDto editUserDto) throws URISyntaxException {

        userService.create(editUserDto.toUser(roleService));
        return Response.temporaryRedirect(new URI("/users")).build();
    }

    // http://localhost:8080/users/update
    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(EditUserDto editUserDto) throws URISyntaxException {

        userService.update(editUserDto.toUser(roleService));
        return Response.temporaryRedirect(new URI("/users")).build();
    }

    // http://localhost:8080/users/1
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") long id) throws Exception {
        userService.remove(userService.findById(id));
        return Response.temporaryRedirect(new URI("/users")).build();
    }
}