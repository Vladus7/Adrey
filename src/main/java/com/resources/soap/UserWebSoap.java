package com.resources.soap;

import com.exeption.UserWasNotChanged;
import com.exeption.UserWasNotCreated;
import com.exeption.UserWasNotDeleted;
import com.model.EditUserDto;
import com.model.UserDto;
import com.model.UserRowDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserWebSoap {

    @WebMethod
    List<UserRowDto> getUsers();

    @WebMethod
    UserDto getUser(@WebParam long id);

    @WebMethod
    void createUser(@WebParam EditUserDto userDto) throws UserWasNotCreated;

    @WebMethod
    void updateUser(@WebParam EditUserDto userDto) throws UserWasNotChanged;

    @WebMethod
    void deleteUser(@WebParam long id) throws UserWasNotDeleted;
}
