package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table( name = "account",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "login", "email"})}
)
@Component
public class User {
   @Id
   @GeneratedValue
   private Long id;
   private String login;
   private String password;
   private String email;
   private String firstName;
   private String lastName;
   @Column(columnDefinition = "date")
   private Date birthday;
   @JsonBackReference
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "roleId")
   private Role role;

   public User() {
   }

   public User(String login, String password, String email, String firstName, String lastName, Date birthday, Role role) {
      this.login = login;
      this.password = password;
      this.email = email;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthday = birthday;
      this.role = role;
   }

   public User(Long id, String login, String password, String email, String firstName, String lastName, Date birthday, Role role) {
      this.id = id;
      this.login = login;
      this.password = password;
      this.email = email;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthday = birthday;
      this.role = role;
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

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
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

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", login='" + login + '\'' +
              ", password='" + password + '\'' +
              ", email='" + email + '\'' +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", birthday=" + birthday +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id.equals(user.id)
              && login.equals(user.login)
              && password.equals(user.password)
              && email.equals(user.email)
              && Objects.equals(firstName, user.firstName)
              && Objects.equals(lastName, user.lastName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, login, password, email, firstName, lastName);
   }
}
