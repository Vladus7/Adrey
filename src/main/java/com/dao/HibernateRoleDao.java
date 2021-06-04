package com.dao;

import com.exeption.TransactionException;
import com.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("roleDao")
public class HibernateRoleDao implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
            criteria.select(criteria.from(Role.class));
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    public Role findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        }
    }

    @Override
    public void create(Role role) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new TransactionException(e);
        }
    }

    @Override
    public void update(Role role) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new TransactionException(e);
        }
    }

    @Override
    public void remove(Role role) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(role);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw new TransactionException(e);
        }
    }

    @Override
    public Role findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
            Root<Role> root = criteria.from(Role.class);
            criteria.where(
                    builder.equal(root.get("name"), name)
            );
            List<Role> roles = session.createQuery(criteria).getResultList();
            return (roles.size() != 0) ? roles.get(0) : null;
        }
    }
}
