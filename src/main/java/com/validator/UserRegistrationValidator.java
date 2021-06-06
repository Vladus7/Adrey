package com.validator;

import com.model.RegistrationUserDto;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("userRegistrationValidator")
public class UserRegistrationValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public Map<String, String> validate(Object object) {
        Map<String, String> errors = new HashMap<>();
        RegistrationUserDto user = (RegistrationUserDto) object;
        //HttpSession session = Objects.requireNonNull(getRequest()).getSession();
        if (user.getLogin().length() < 3) {
            errors.put("login", "massages");
        }
        if (Objects.nonNull(userService.findByLogin(user.getLogin()))) {
            errors.put("login", "massages.isUsed");
        }
        if (user.getPassword().length() < 3) {
            errors.put("password", "massages");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.put("confirmPassword", "massages");
        }
        if (!user.getEmail().matches("^(.+)@(.+)$")) {
            errors.put("email", "massages");
        }
        if (Objects.nonNull(userService.findByEmail(user.getEmail()))) {
            errors.put("email", "massages.isUsed");
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
//        if (!session.getAttribute("captcha").equals(user.getCaptcha())) {
//            errors.put("captcha", "massages");
//        }
        return errors;
    }

    private HttpServletRequest getRequest() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof NativeWebRequest) {
            return (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
        }
        return null;
    }
}
