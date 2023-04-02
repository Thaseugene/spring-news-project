package com.spring.news.service.impl;

import com.spring.news.model.News;
import com.spring.news.repository.NewsRepository;
import com.spring.news.repository.NewsRepositoryException;
import com.spring.news.service.NewsService;
import com.spring.news.service.exception.NewsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    private NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> takeAllNews() throws NewsServiceException {
        try {
            return newsRepository.getAllNews();
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }

    }

    @Override
    public List<News> takeLatestNews() throws NewsServiceException {
        try {
            return newsRepository.getLatestNews();
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    public News takeNewsById(int id) throws NewsServiceException {
        try {
            return newsRepository.getNewsById(id);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    public void updateNews(News news) throws NewsServiceException {
        try {
            newsRepository.updateNews(news);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    public void deactivateNews(News news) throws NewsServiceException {
        try {
            newsRepository.deactivateNews(news);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }
}
