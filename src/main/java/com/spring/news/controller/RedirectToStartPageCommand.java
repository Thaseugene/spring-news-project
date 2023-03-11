package com.spring.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectToStartPageCommand {

    @RequestMapping("/")
    public String showLoginPage() {
        return "redirect:news/list";
    }
}
