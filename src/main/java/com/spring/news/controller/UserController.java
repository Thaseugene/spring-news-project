package com.spring.news.controller;


import com.spring.news.model.user.User;
import com.spring.news.service.UserService;
import com.spring.news.service.exception.UserServiceException;
import com.spring.news.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final String VIEW_PARAM_NAME = "presentation";
    public static final String REG_VIEW_NAME = "registration";
    public static final String AUTH_VIEW_NAME = "authentication";
    public static final String ERROR_PARAM_NAME = "error";
    public static final String MESSAGE_PARAM_NAME = "message";
    public static final String REDIRECT_REG_SHOW_REG = "redirect:/user/showReg";
    public static final String REDIRECT_ERROR_PAGE = "redirect:/error/showError";
    public static final String ACCOUNT_SUCCESSFULLY_CREATED = "Account successfully created";
    public static final String BASE_VIEW = "base-view";
    public static final String USER_FORM = "user";
    public static final String REDIRECT_FORMAT = "redirect:%s";
    public static final String REQUEST_REFERRER_NAME = "Referer";
    public static final String ERROR_FORMAT = "An error occurred: %s";


    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public UserController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showAuth")
    public String showAuthForm(Model model) {
        model.addAttribute(VIEW_PARAM_NAME, AUTH_VIEW_NAME);
        return BASE_VIEW;
    }

    @GetMapping("/showReg")
    public String showRegForm(Model model, @ModelAttribute("user") User user) {
        model.addAttribute(VIEW_PARAM_NAME, REG_VIEW_NAME);
        return BASE_VIEW;
    }

    @PostMapping("/processRegForm")
    public String processForm(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              Model model) {
        try {
            userValidator.validate(user, result);
            if (result.hasErrors()) {
                model.addAttribute(VIEW_PARAM_NAME, REG_VIEW_NAME);
                model.addAttribute(USER_FORM, user);
                return BASE_VIEW;
            } else {
                userService.addNewUser(user);
                model.addAttribute(MESSAGE_PARAM_NAME, ACCOUNT_SUCCESSFULLY_CREATED);
                return REDIRECT_REG_SHOW_REG;
            }
        } catch (UserServiceException e) {
            model.addAttribute(ERROR_PARAM_NAME, e.getMessage());
            return REDIRECT_ERROR_PAGE;
        }
    }

    @PostMapping("/changeLocale")
    public String changeLocale(@RequestParam("lang") String language,
                               HttpServletRequest request) {

        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale(language));
        String referringUrl = request.getHeader(REQUEST_REFERRER_NAME);
        return String.format(REDIRECT_FORMAT, referringUrl);
    }


    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute(ERROR_PARAM_NAME, String.format(ERROR_FORMAT, ex.getMessage()));
        return REDIRECT_ERROR_PAGE;
    }

}
