package com.spring.news.controller;


import com.spring.news.model.news.News;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/edit")
public class NewsEditController {

    public static final String REDIRECT_NEWS_LIST = "redirect:/news/list";
    public static final String REDIRECT_ERROR_PAGE = "redirect:/error/showError";
    public static final String BASE_VIEW = "base-view";
    public static final String ERROR_PARAM_NAME = "error";
    public static final String VIEW_PARAM_NAME = "presentation";
    public static final String NEWS_PARAM_NAME = "news";
    public static final String ADD_VIEW_NAME = "addNews";
    public static final String EDIT_VIEW_NAME = "editNews";
    public static final String ERROR_FORMAT = "An error occurred: %s";
    public static final String MESSAGE_PARAM_NAME = "message";
    public static final String NEWS_ADDED = "News successfully added";
    public static final String NEWS_UPDATED = "News successfully updated";
    public static final String REDIRECT_NEWS_EDIT = "redirect:/news/edit/%s";
    public static final String REDIRECT_NEWS_ADD_NEWS = "redirect:/news/edit/addNews";
    private final NewsService newsService;

    @Autowired
    public NewsEditController(NewsService newsService) {
        this.newsService = newsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/deactivate")
    public String deactivateNews(@RequestParam("newsIds") List<Integer> newsIds,
                                 Model model) {

        try {
            newsService.deactivateNews(newsIds);
            return REDIRECT_NEWS_LIST;
        } catch (NewsServiceException e) {
            model.addAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @PostMapping("/processAddForm")
    public String processAddForm(@Valid @ModelAttribute("news") News news,
                                 BindingResult result,
                                 Model model,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + NEWS_PARAM_NAME, result);
                redirectAttributes.addFlashAttribute(NEWS_PARAM_NAME, news);
                return REDIRECT_NEWS_ADD_NEWS;
            } else {
                news.setAuthor(((UserDetailsImpl) ((Authentication) principal).getPrincipal()).getUser());
                newsService.addNews(news);
                redirectAttributes.addFlashAttribute(MESSAGE_PARAM_NAME, NEWS_ADDED);
                return REDIRECT_NEWS_LIST;
            }
        } catch (NewsServiceException e) {
            redirectAttributes.addFlashAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @PostMapping("/processEditForm")
    public String processEditForm(@Valid @ModelAttribute("news") News news,
                                  BindingResult result,
                                  Model model,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + NEWS_PARAM_NAME, result);
                redirectAttributes.addFlashAttribute(NEWS_PARAM_NAME, news);
                return String.format(REDIRECT_NEWS_EDIT, news.getId());
            } else {
                news.setAuthor(((UserDetailsImpl) ((Authentication) principal).getPrincipal()).getUser());
                newsService.updateNews(news);
                redirectAttributes.addFlashAttribute(MESSAGE_PARAM_NAME, NEWS_UPDATED);
                return REDIRECT_NEWS_LIST;
            }
        } catch (NewsServiceException e) {
            redirectAttributes.addFlashAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute(ERROR_PARAM_NAME, String.format(ERROR_FORMAT, ex.getMessage()));
        return REDIRECT_ERROR_PAGE;
    }
}

