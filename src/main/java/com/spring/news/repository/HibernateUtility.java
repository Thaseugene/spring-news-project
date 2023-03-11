package com.spring.news.repository;

import com.spring.news.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtility {

    private static HibernateUtility instance;
    private final SessionFactory sessionFactory;

    private HibernateUtility() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class);

            Metadata metadata = metadataSources.getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static HibernateUtility getInstance() {
        if (instance == null) {
            instance = new HibernateUtility();
        }
        return instance;
    }
}
