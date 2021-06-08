package com.validator;

import com.model.EditUserDto;
import com.model.Role;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("userEditValidator")
public class UserEditValidator implements Validator {
    @Autowired
    private RoleService roleService;

    @Override
    public Map<String, String> validate(Object object) {
        Map<String, String> errors = new HashMap<>();
        EditUserDto user = (EditUserDto) object;
       // HttpSession session = attr.getRequest().getSession();
        if (!user.getPassword().isEmpty() && user.getPassword().length() < 3) {
            errors.put("password", "massages");
        }
        if (!user.getPassword().isEmpty() && !user.getPassword().equals(user.getConfirmPassword())) {
            errors.put("confirmPassword", "massages");
        }
        if (!user.getEmail().matches("^(.+)@(.+)$")) {
            errors.put("email", "massages");
        }
        if (user.getFirstName().trim().length() < 3
                || user.getFirstName().matches(".*\\d.*")) {
            errors.put("firstName", "massages");
        }
        if (user.getFirstName().trim().length() < 3
                || user.getLastName().matches(".*\\d.*")) {
            errors.put("lastName", "massages");
        }
        if (Objects.isNull(user.getBirthday()) || user.getBirthday().after(new Date())) {
            errors.put("birthday", "massages");
        }
        if (checkUserRole(user.getRole())) {
            errors.put("role", "massages");
        }
//        if (!session.getAttribute("captcha").equals(user.getCaptcha())) {
//            errors.put("captcha", "massages");
//        }
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
