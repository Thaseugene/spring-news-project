package com.spring.news.repository.impl;

import com.spring.news.model.News;
import com.spring.news.repository.NewsRepository;
import com.spring.news.repository.NewsRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class NewsRepositoryImpl implements NewsRepository {


    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private NewsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static final String ALL_NEWS_QUERY = "from News";

    @Override
    public List<News> getAllNews() throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<News> query = session.createQuery(ALL_NEWS_QUERY, News.class);
            return query.list();
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }


    @Override
    public List<News> getLatestNews() throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<News> query = session.createQuery("FROM News News ORDER BY News.publicationDate DESC",
                    News.class);
            query.setMaxResults(6);
            return query.list();
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public News getNewsById(int id) throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<News> query = session.createQuery("from News where id = :id", News.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public void updateNews(News news) throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(news);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public void deactivateNews(News news) throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            news.setActive(false);
            session.saveOrUpdate(news);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }
}

