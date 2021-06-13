package com.validator;

import com.model.EditUserDto;
import com.model.Role;
import com.service.CaptchaService;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("userEditValidator")
public class UserEditValidator implements Validator {
    @Autowired
    private RoleService roleService;
    @Autowired
    private Properties properties;
    @Autowired
    private CaptchaService captchaService;

    @Override
    public Map<String, String> validate(Object object) {
        Map<String, String> errors = new HashMap<>();
        EditUserDto user = (EditUserDto) object;
        if (!user.getPassword().isEmpty() && user.getPassword().length() < 3) {
            errors.put("password", properties.getProperty("password.massages"));
        }
        if (!user.getPassword().isEmpty() && !user.getPassword().equals(user.getConfirmPassword())) {
            errors.put("confirmPassword", properties.getProperty("confirmPassword.massages"));
        }
        if (!user.getEmail().matches("^(.+)@(.+)$")) {
            errors.put("email", properties.getProperty("email.massages"));
        }
        if (user.getFirstName().trim().length() < 3
                || user.getFirstName().matches(".*\\d.*")) {
            errors.put("firstName", properties.getProperty("firstName.massages"));
        }
        if (user.getFirstName().trim().length() < 3
                || user.getLastName().matches(".*\\d.*")) {
            errors.put("lastName", properties.getProperty("lastName.massages"));
        }
        if (Objects.isNull(user.getBirthday()) || user.getBirthday().after(new Date())) {
            errors.put("birthday", properties.getProperty("birthday.massages"));
        }
        if (checkUserRole(user.getRole())) {
            errors.put("role", properties.getProperty("role.massages"));
        }
        System.out.println(user.getCaptcha());
        System.out.println(user.getCaptchaId());
        if (!captchaService.validCaptcha(user.getCaptchaId(), user.getCaptcha())) {
            errors.put("captcha", properties.getProperty("captcha.massages"));
        }
        return errors;
    }

    private boolean checkUserRole (String role) {
        for (Role gotRole: roleService.findAll()) {
            if (role.equalsIgnoreCase(gotRole.getName())) {
                return false;
            }
        }
        return true;
    }
}
