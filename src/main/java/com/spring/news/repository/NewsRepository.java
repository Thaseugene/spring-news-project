package com.spring.news.repository;

import com.spring.news.model.news.News;
import com.spring.news.repository.exception.NewsRepositoryException;

import java.util.List;

public interface NewsRepository {

    List<News> getAllNews(int offset, int pageSize) throws NewsRepositoryException;
    List<News> getLatestNews() throws NewsRepositoryException;

    News getNewsById(int id) throws NewsRepositoryException;

    Long getTotalNewsCount() throws NewsRepositoryException;

    void updateNews(News news) throws NewsRepositoryException;

    void deactivateNews(List<Integer> newsIdList) throws NewsRepositoryException;

    void saveNews(News news) throws NewsRepositoryException;
}
