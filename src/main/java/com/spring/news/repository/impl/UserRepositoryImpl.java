package com.spring.news.repository.impl;

import com.spring.news.model.User;
import com.spring.news.repository.ConnectionBuilder;
import com.spring.news.repository.HibernateUtility;
import com.spring.news.repository.UserRepository;
import com.spring.news.repository.UserRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private ConnectionBuilder connectionPool;

    private final String salt = BCrypt.gensalt();
    SessionFactory sessionFactory = HibernateUtility.getInstance().getSessionFactory();

    private static final Logger LOGGER = LogManager.getRootLogger();
    public static final String COLUMN_LABEL_LOGIN = "login";

    private UserRepositoryImpl() {

    }

    private static final String GET_USER_ID_QUERY = "from User where login = :login";

    @Override
    public int takeUsersIdByLogin(String login, String password) throws UserRepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(GET_USER_ID_QUERY, User.class);
            query.setParameter(COLUMN_LABEL_LOGIN, login);
            User user = query.uniqueResult();
            if (user != null && verifyPassword(password, user.getPassword())) {
                return user.getId();
            } else {
                return 0;
            }
        } catch (HibernateException e) {
            LOGGER.warn("Problems with taking info from data or another exception occurred", e);
            throw new UserRepositoryException("Database getting info problems", e);
        }
    }


    public boolean checkIsLoginExists(String login) throws UserRepositoryException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.get(COLUMN_LABEL_LOGIN), login));
            return !session.createQuery(query).getResultList().isEmpty();
        } catch (HibernateException e) {
            LOGGER.warn("Problems with taking info from data or another exception occurred", e);
            throw new UserRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public User takeUserById(int id) throws UserRepositoryException {
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);
            LOGGER.info(user.getName());
        } catch (Exception e) {
            LOGGER.warn("Problems with taking info from data or another exception occurred", e);
            throw new UserRepositoryException("Database getting info problems", e);
        }
        return user;
    }

    @Override
    public void addNewUser(User user) throws UserRepositoryException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.warn("Problems with adding info to data or another exception occurred", e);
            throw new UserRepositoryException("Failed to add user to database", e);
        }

    }

    private boolean verifyPassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }

}
