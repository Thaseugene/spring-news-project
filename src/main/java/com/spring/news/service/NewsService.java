package com.spring.news.service;

import com.spring.news.model.news.News;
import com.spring.news.service.exception.NewsServiceException;

import java.util.List;

public interface NewsService {

    List<News> takeAllNews(int page, int pageSize) throws NewsServiceException;
    List<News> takeLatestNews() throws NewsServiceException;

    News takeNewsById(int id) throws NewsServiceException;
    Long getTotalNewsCount() throws NewsServiceException;

    void updateNews(News news) throws NewsServiceException;

    void deactivateNews(List<Integer> newsIdList) throws NewsServiceException;

    void addNews(News news) throws NewsServiceException;
}
