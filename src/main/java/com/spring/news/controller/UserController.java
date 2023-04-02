package com.spring.news.controller;


import com.spring.news.model.User;
import com.spring.news.service.exception.AlreadyExistsException;
import com.spring.news.service.exception.IncorrectLoginException;
import com.spring.news.service.UserService;
import com.spring.news.service.exception.UserServiceException;
import com.spring.news.validation.AuthFormValidator;
import com.spring.news.validation.FormValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {



    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    public static final String PRESENTATION = "presentation";
    public static final String REGISTRATION = "registration";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String REDIRECT_REG_SHOW_REG = "redirect:/user/showReg";
    public static final String REDIRECT_ERROR_SHOW_ERROR = "redirect:/error/showError";
    public static final String PASSWORD_ARE_NOT_EQUAL = "Passwords are not equal";
    public static final String ACCOUNT_SUCCESSFULLY_CREATED = "Account successfully created";
    public static final String BASE_VIEW = "base-view";
    public static final String USER_FORM = "user";
    public static final String REDIRECT_SHOW_AUTH = "redirect:/user/showAuth";
    public static final String REDIRECT_NEWS_LIST = "redirect:/news/list";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/showAuth")
    public String showAuthForm(Model model) {
        model.addAttribute("presentation", "authentication");
        return "base-view";
    }

    @RequestMapping("/showReg")
    public String showRegForm(Model model) {
        model.addAttribute(PRESENTATION, REGISTRATION);
        model.addAttribute(USER_FORM, new User());
        return BASE_VIEW;
    }

    @RequestMapping("/processForm")
    public String processForm(@RequestParam("login") String login,
                              @RequestParam("password") String password,
                              Model model,
                              HttpSession session) {
        try {
            AuthFormValidator validator = new AuthFormValidator(login, password);
            validator.validate();
            User user = userService.getUserByLoginAndPass(login, password);
            setSessionAttributes(user, session);
            return REDIRECT_NEWS_LIST;
        } catch (FormValidationException | IncorrectLoginException e) {
            model.addAttribute("error", e.getMessage());
            return REDIRECT_SHOW_AUTH;
        } catch (UserServiceException e) {
            model.addAttribute("error", e.getMessage());
            return REDIRECT_ERROR_SHOW_ERROR;
        }
    }

    @RequestMapping("/processRegForm")
    public String processForm(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute(PRESENTATION, REGISTRATION);
                model.addAttribute(USER_FORM, user);
                return BASE_VIEW;
            } else if (!confirmPassword(user)) {
                model.addAttribute(ERROR, PASSWORD_ARE_NOT_EQUAL);
                return REDIRECT_REG_SHOW_REG;
            } else {
                userService.addNewUser(user);
                model.addAttribute(MESSAGE, ACCOUNT_SUCCESSFULLY_CREATED);
                return REDIRECT_REG_SHOW_REG;
            }
        } catch (UserServiceException e) {
            model.addAttribute(ERROR, e.getMessage());
            return REDIRECT_ERROR_SHOW_ERROR;
        } catch (AlreadyExistsException e) {
            model.addAttribute(ERROR, e.getMessage());
            return REDIRECT_REG_SHOW_REG;
        }
    }

    private boolean confirmPassword(User user) {
        return Objects.equals(user.getPassword(), user.getConfirmPassword());
    }

    private void setSessionAttributes(User user, HttpSession session) {
        session.setAttribute("name", user.getUserDetails().getName());
        session.setAttribute("surname", user.getUserDetails().getSurname());
        session.setAttribute("role", user.getRole());
        session.setAttribute("email", user.getEmail());
    }
}
