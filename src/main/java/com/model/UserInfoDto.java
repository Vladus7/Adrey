package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class UserInfoDto {
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String role;
    private int age;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = user.getBirthday();
        this.role = user.getRole().getName();
        this.age = Period.between(new Date(birthday.getTime()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(), LocalDate.now()).getYears();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDto that = (UserInfoDto) o;
        return age == that.age && id.equals(that.id) && login.equals(that.login) && email.equals(that.email) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && birthday.equals(that.birthday) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, firstName, lastName, birthday, role, age);
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", role='" + role + '\'' +
                ", age=" + age +
                '}';
    }
}
