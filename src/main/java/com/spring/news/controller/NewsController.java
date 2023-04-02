package com.spring.news.controller;


import com.spring.news.service.NewsService;
import com.spring.news.service.exception.NewsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/list")
    public String showNewsList(Model model) {
        try {
            model.addAttribute("presentation", "newsList");
            model.addAttribute("newsList", newsService.takeAllNews());
            model.addAttribute("latestNews", newsService.takeLatestNews());
            return "base-view";
        } catch (NewsServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/error/showError";
        }
    }

    @RequestMapping("/news")
    public String showNews(Model model) {
        return null;
    }
}
