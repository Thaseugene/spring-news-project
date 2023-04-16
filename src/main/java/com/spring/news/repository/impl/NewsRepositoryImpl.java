package com.spring.news.repository.impl;

import com.spring.news.model.news.News;
import com.spring.news.repository.NewsRepository;
import com.spring.news.repository.exception.NewsRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    public static final String NEWS_IDS_PARAM_NAME = "newsIds";
    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Autowired
    private NewsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static final String ALL_NEWS_QUERY = "FROM News WHERE isActive = true ORDER By publicationDate DESC";
    @Override
    public List<News> getAllNews(int offset, int pageSize) throws NewsRepositoryException {
        try {

            Session session = sessionFactory.getCurrentSession();
            Query<News> query = session.createQuery(ALL_NEWS_QUERY, News.class);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }


    public static final String SELECT_LATEST_QUERY = "FROM News WHERE isActive = true ORDER BY publicationDate DESC";
    @Override
    public List<News> getLatestNews() throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<News> query = session.createQuery(SELECT_LATEST_QUERY,
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

    public static final String SELECT_COUNT_QUERY = "SELECT COUNT(*) FROM News WHERE isActive=true";
    @Override
    public Long getTotalNewsCount() throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Long> query = session.createQuery(SELECT_COUNT_QUERY, Long.class);
            return query.uniqueResult();
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

    public static final String DEACTIVATE_NEWS_QUERY = "UPDATE News SET isActive = false WHERE id IN :newsIds";
    @Override
    public void deactivateNews(List<Integer> newsIdList) throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(DEACTIVATE_NEWS_QUERY);
            query.setParameterList(NEWS_IDS_PARAM_NAME, newsIdList);
            int updatedCount = query.executeUpdate();
            LOGGER.debug("{} news items updated", updatedCount);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }

    @Override
    public void saveNews(News news) throws NewsRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(news);
        } catch (HibernateException e) {
            LOGGER.warn("Problems with getting info from data or another exception occurred: {} - {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new NewsRepositoryException("Database getting info problems", e);
        }
    }
}

