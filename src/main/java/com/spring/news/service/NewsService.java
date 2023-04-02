package com.spring.news.service;

import com.spring.news.model.News;
import com.spring.news.service.exception.NewsServiceException;

import java.util.List;

public interface NewsService {

    List<News> takeAllNews() throws NewsServiceException;
    List<News> takeLatestNews() throws NewsServiceException;

    News takeNewsById(int id) throws NewsServiceException;

    void updateNews(News news) throws NewsServiceException;

    void deactivateNews(News news) throws NewsServiceException;
}
