package com.spring.news.controller;


import com.spring.news.model.news.News;
import com.spring.news.model.user.User;
import com.spring.news.security.UserDetailsImpl;
import com.spring.news.service.NewsService;
import com.spring.news.service.exception.NewsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsViewController {

    public static final String REDIRECT_NEWS_LIST = "redirect:/news/list";
    public static final String REDIRECT_ERROR_PAGE = "redirect:/error/showError";
    public static final String BASE_VIEW = "base-view";
    public static final String ERROR_PARAM_NAME = "error";
    public static final String VIEW_PARAM_NAME = "presentation";
    public static final String NEWS_PARAM_NAME = "news";
    public static final String NEWS_LIST_PARAM = "newsList";
    public static final String CURRENT_PAGE_PARAM_NAME = "currentPage";
    public static final String TOTAL_PAGES_PARAM_NAME = "totalPages";
    public static final String PAGE_SIZE_PARAM_NAME = "pageSize";
    public static final String LATEST_NEWS_PARAM_NAME = "latestNews";
    public static final String VIEW_NEWS_PARAM = "viewNews";
    public static final String EDIT_NEWS_PARAM = "editNews";
    public static final String ADD_NEWS_PARAM = "addNews";
    public static final String ERROR_FORMAT = "An error occurred: %s";
    private final NewsService newsService;

    @Autowired
    public NewsViewController(NewsService newsService) {
        this.newsService = newsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String showNewsList(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                               Model model) {
        try {
            if (page <= 0 || pageSize <= 0) {
                page = 1;
                pageSize = 5;
            }

            List<News> allNews = newsService.takeAllNews(page, pageSize);
            List<News> latestNews = newsService.takeLatestNews();

            Long totalNewsCount = newsService.getTotalNewsCount();
            int totalPages = (int) Math.ceil((double) totalNewsCount / pageSize);

            model.addAttribute(VIEW_PARAM_NAME, NEWS_LIST_PARAM);
            model.addAttribute(CURRENT_PAGE_PARAM_NAME, page);
            model.addAttribute(TOTAL_PAGES_PARAM_NAME, totalPages);
            model.addAttribute(PAGE_SIZE_PARAM_NAME, pageSize);
            model.addAttribute(NEWS_LIST_PARAM, allNews);
            model.addAttribute(LATEST_NEWS_PARAM_NAME, latestNews);

            return BASE_VIEW;
        } catch (NewsServiceException e) {
            model.addAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/view/{newsId}")
    public String showNews(@PathVariable int newsId, Model model) {
        try {
            News news = newsService.takeNewsById(newsId);
            List<News> latestNews = newsService.takeLatestNews();
            model.addAttribute(VIEW_PARAM_NAME, VIEW_NEWS_PARAM);
            model.addAttribute(NEWS_PARAM_NAME, news);
            model.addAttribute(LATEST_NEWS_PARAM_NAME, latestNews);
            return BASE_VIEW;
        } catch (NewsServiceException e) {
            model.addAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/edit/{newsId}")
    public String showEditPageForm(@PathVariable int newsId, Model model) {
        try {
            model.addAttribute(VIEW_PARAM_NAME, EDIT_NEWS_PARAM);
            if (!model.containsAttribute(NEWS_PARAM_NAME)) {
                News news = newsService.takeNewsById(newsId);
                model.addAttribute(NEWS_PARAM_NAME, news);
            }
            return BASE_VIEW;
        } catch (NewsServiceException e) {
            model.addAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/edit/addNews")
    public String showAddNewsForm(Model model) {
        model.addAttribute(VIEW_PARAM_NAME, ADD_NEWS_PARAM);
        if (!model.containsAttribute(NEWS_PARAM_NAME)) {
            model.addAttribute(NEWS_PARAM_NAME, new News());
        }
        return BASE_VIEW;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute(ERROR_PARAM_NAME, String.format(ERROR_FORMAT, ex.getMessage()));
        return REDIRECT_ERROR_PAGE;
    }
}

