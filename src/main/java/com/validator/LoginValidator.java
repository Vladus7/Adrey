package com.validator;

import com.model.LoginUserDto;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Service("loginValidator")
public class LoginValidator implements Validator{
    @Autowired
    private UserService userService;
    @Autowired
    private Properties properties;

    @Override
    public Map<String, String> validate(Object object) {
        Map<String, String> errors = new HashMap<>();
        LoginUserDto userDto = (LoginUserDto) object;
        User user = userService.findByLogin(userDto.getLogin());
        if (Objects.isNull(user)) {
            errors.put("login", properties.getProperty("login.massages.isNotUsed"));
            return errors;
        }
        if (Objects.isNull(userDto.getPassword()) || !userDto.getPassword().equals(user.getPassword())) {
            errors.put("password", properties.getProperty("password.massages"));
        }
        return errors;
    }
}
