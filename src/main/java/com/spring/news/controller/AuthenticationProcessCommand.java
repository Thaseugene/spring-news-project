package com.spring.news.controller;

import com.spring.news.model.User;
import com.spring.news.service.IncorrectLoginException;
import com.spring.news.service.UserService;
import com.spring.news.service.UserServiceException;
import com.spring.news.validation.FormValidationException;
import com.spring.news.validation.AuthFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthenticationProcessCommand {

    private final UserService userService;

    @Autowired
    private AuthenticationProcessCommand(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/showAuth")
    public String showAuthForm(Model model) {
        model.addAttribute("presentation", "authentication");
        return "base-view";
    }

    @RequestMapping("/processForm")
    public String processForm(@RequestParam("login") String login, @RequestParam("password") String password, Model model, HttpSession session) {
        try {
            AuthFormValidator validator = new AuthFormValidator(login, password);
            validator.validate();
            User user = userService.getUserByLoginAndPass(login, password);
            setSessionAttributes(user, session);
            return "redirect:/news/list";
        } catch (FormValidationException | IncorrectLoginException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/showAuth";
        } catch (UserServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/error/showError";
        }
    }

    private void setSessionAttributes(User user, HttpSession session) {
        session.setAttribute("name", user.getName());
        session.setAttribute("surname", user.getSurname());
        session.setAttribute("role", user.getRole());
        session.setAttribute("email", user.getEmail());
    }

}
