package com.spring.news.repository;

import com.spring.news.model.News;

import java.util.List;

public interface NewsRepository {

    List<News> getAllNews() throws NewsRepositoryException;
    List<News> getLatestNews() throws NewsRepositoryException;

    News getNewsById(int id) throws NewsRepositoryException;

    void updateNews(News news) throws NewsRepositoryException;

    void deactivateNews(News news) throws NewsRepositoryException;
}
