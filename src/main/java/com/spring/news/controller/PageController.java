package com.spring.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    public static final String REDIRECT_NEWS_LIST = "redirect:news/list";
    public static final String ERROR_PAGE = "error-page";
    public static final String ACCESS_DENIED_PAGE = "access-denied-page";

    @RequestMapping("/")
    public String showLoginPage() {
        return REDIRECT_NEWS_LIST;
    }

    @RequestMapping("/error/showError")
    public String showError() {
        return ERROR_PAGE;
    }

    @RequestMapping("/error/accessDenied")
    public String showAccessDenied() {
        return ACCESS_DENIED_PAGE;
    }
}
