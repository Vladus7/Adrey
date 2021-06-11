package com.resources.soap;

import com.exeption.UserWasNotChanged;
import com.exeption.UserWasNotCreated;
import com.exeption.UserWasNotDeleted;
import com.model.EditUserDto;
import com.model.User;
import com.model.UserDto;
import com.model.UserRowDto;
import com.service.ConvertorUsersToUserRowBeans;
import com.service.RoleService;
import com.service.UserService;

import com.validator.UserAddValidator;
import com.validator.UserEditValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebService(endpointInterface = "com.resources.soap.UserWebSoap",
    serviceName = "UserWebServiceSoap")
public class UserWebSoapImpl implements UserWebSoap{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserAddValidator userAddValidator;
    @Autowired
    private UserEditValidator userEditValidator;
    @Autowired
    private ConvertorUsersToUserRowBeans convertorUsersToUserRowBeans;

    @Override
    public List<UserRowDto> getUsers() {
        return convertorUsersToUserRowBeans.convert(
                userService.findAll());
    }

    @Override
    public UserDto getUser(long id) {
        return new UserDto(userService.findById(id));
    }

    @Override
    public void createUser(EditUserDto userDto) throws UserWasNotCreated {
        Map<String,String> errors = userAddValidator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new UserWasNotCreated("Values are incorrect:" + errors);
        }
        userService.create(userDto.toUser(roleService));
    }

    @Override
    public void updateUser(EditUserDto userDto) throws UserWasNotChanged {
        Map<String,String> errors = userEditValidator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new UserWasNotChanged("Values are incorrect:" + errors);
        }
        userService.update(userDto.toUser(roleService));
    }

    @Override
    public void deleteUser(long id) throws UserWasNotDeleted {
        User user = userService.findById(id);
        if (Objects.isNull(user)) {
            throw  new UserWasNotDeleted("User was not defined");
        }
        userService.remove(user);
    }
}
