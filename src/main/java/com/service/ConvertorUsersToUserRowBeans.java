package com.service;

import com.model.User;
import com.model.UserRowDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("convertorUsersToUserRowBeans")
public class ConvertorUsersToUserRowBeans {

    public List<UserRowDto> convert(List<User> inputUsers) {
        List<UserRowDto> users = new ArrayList<>();
        for (User user : inputUsers) {
            users.add(new UserRowDto(user.getId(), user.getLogin(),
                    user.getFirstName(), user.getLastName(),
                    getAge(user.getBirthday()), user.getRole()
            ));
        }
        return users;
    }

    private int getAge(Date birthday) {
        return Period.between(new Date(birthday.getTime()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(), LocalDate.now()).getYears();
    }

}
