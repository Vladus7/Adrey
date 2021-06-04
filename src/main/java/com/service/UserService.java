package com.service;

import com.dao.UserDao;
import com.exeption.TransactionException;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserService {
   @Autowired
   private UserDao userDao;

   @Transactional(readOnly = true)
   public User findById(Long id) {
     return userDao.findById(id);
   }

   @Transactional(rollbackFor = { TransactionException.class })
   public void create(User user) {
      userDao.create(user);
   }

   @Transactional(rollbackFor = { TransactionException.class })
   public void update(User user) {
      userDao.update(user);
   }

   @Transactional(rollbackFor = { TransactionException.class })
   public void remove(User user) {
      userDao.remove(user);
   }

   @Transactional(readOnly = true)
   public List<User> findAll() {
      return userDao.findAll();
   }

   @Transactional(readOnly = true)
   public User findByLogin(String login) {
      return userDao.findByLogin(login);
   }

   @Transactional(readOnly = true)
   public User findByEmail(String email) {
      return userDao.findByEmail(email);
   }
}
