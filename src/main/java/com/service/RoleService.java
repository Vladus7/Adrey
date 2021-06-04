package com.service;

import com.dao.RoleDao;
import com.exeption.TransactionException;
import com.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Transactional(rollbackFor = { TransactionException.class })
    public void create(Role role) {
        roleDao.create(role);
    }

    @Transactional(rollbackFor = { TransactionException.class })
    public void update(Role role) {
        roleDao.update(role);
    }

    @Transactional(rollbackFor = { TransactionException.class })
    public void remove(Role role) {
        roleDao.remove(role);
    }

    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
