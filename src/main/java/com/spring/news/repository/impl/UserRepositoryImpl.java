package com.spring.news.repository.impl;

import com.spring.news.model.User;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.UserRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LogManager.getRootLogger();
    public static final String COLUMN_LABEL_LOGIN = "login";

    @Autowired
    private UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    private static final String GET_USER_ID_QUERY = "from User where login = :login";

    @Override
    public Optional<User> takeUserByLogin(String login) throws UserRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery(GET_USER_ID_QUERY, User.class);
            query.setParameter(COLUMN_LABEL_LOGIN, login);
            return Optional.ofNullable(query.uniqueResult());
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from database or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new UserRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public void addNewUser(User user) throws UserRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(user);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with adding info to database or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new UserRepositoryException("Failed to add user to database", e);
        }
    }

    @Override
    public void updateUser(User user) throws UserRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with adding info to database or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new UserRepositoryException("Failed to add user to database", e);
        }
    }

    @Override
    public void deleteUser(User user) throws UserRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with adding info to database or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new UserRepositoryException("Failed to add user to database", e);
        }
    }

}
