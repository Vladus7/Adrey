package com.service;

import com.dao.RoleDao;
import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.model.User user = userDao.findByLogin(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return User.builder()
                .username(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(roleDao.findById(user.getId()).getName().toUpperCase(Locale.ROOT)).build();
    }
}