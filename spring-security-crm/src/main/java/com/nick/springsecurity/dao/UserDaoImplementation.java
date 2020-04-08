package com.nick.springsecurity.dao;

import com.nick.springsecurity.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImplementation implements UserDao {

    // need to inject the session factory
    @Autowired
    @Qualifier(value = "securitySessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public User findByUserName(String userName) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using username
        Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", userName);
        User user = null;
        try {
            user = theQuery.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    public void save(User user) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create the user ... finally LOL
        currentSession.saveOrUpdate(user);
    }
}
