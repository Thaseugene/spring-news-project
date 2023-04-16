package com.spring.news.service.impl;

import com.spring.news.model.news.News;
import com.spring.news.repository.NewsRepository;
import com.spring.news.repository.exception.NewsRepositoryException;
import com.spring.news.service.NewsService;
import com.spring.news.service.exception.NewsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;


    @Autowired
    private NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    @Transactional
    public List<News> takeAllNews(int page, int pageSize) throws NewsServiceException {
        try {
            int offset = (page - 1) * pageSize;
            return newsRepository.getAllNews(offset, pageSize);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }

    }

    @Override
    @Transactional
    public List<News> takeLatestNews() throws NewsServiceException {
        try {
            return newsRepository.getLatestNews();
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    @Transactional
    public News takeNewsById(int id) throws NewsServiceException {
        try {
            return newsRepository.getNewsById(id);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    @Transactional
    public Long getTotalNewsCount() throws NewsServiceException {
        try {
            return newsRepository.getTotalNewsCount();
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateNews(News news) throws NewsServiceException {
        try {
            news.setActive(true);
            news.setPublicationDate(new Date());
            newsRepository.updateNews(news);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    @Transactional
    public void deactivateNews(List<Integer> NewsIdList) throws NewsServiceException {
        try {
            newsRepository.deactivateNews(NewsIdList);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    @Transactional
    public void addNews(News news) throws NewsServiceException {
        try {
            news.setActive(true);
            news.setCreationDate(new Date());
            news.setPublicationDate(new Date());
            newsRepository.saveNews(news);
        } catch (NewsRepositoryException e) {
            throw new NewsServiceException(e);
        }
    }
}
