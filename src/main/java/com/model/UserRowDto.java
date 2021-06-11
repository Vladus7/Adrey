package com.model;

import javax.ws.rs.core.GenericType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "userRowDto")
public class UserRowDto {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private int age;
    private String role;

    public UserRowDto() {
    }

    public UserRowDto(Long id, String login, String firstName, String lastName, int age, Role role) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.role = role.getName();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRowDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRowDto that = (UserRowDto) o;
        return age == that.age && id.equals(that.id) && login.equals(that.login) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstName, lastName, age, role);
    }
}
