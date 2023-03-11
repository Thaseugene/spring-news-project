package com.spring.news.controller;

import com.spring.news.repository.HibernateUtility;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
@Getter
@Setter
public class HibernateSessionStartListener implements ServletContextListener {

    SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        setSessionFactory(HibernateUtility.getInstance().getSessionFactory());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        getSessionFactory().close();
    }



}
