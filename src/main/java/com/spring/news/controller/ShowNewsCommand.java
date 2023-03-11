package com.spring.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class ShowNewsCommand {


    @RequestMapping("/list")
    public String showNewsList(Model model) {
        model.addAttribute("presentation", "newsList");
        return "base-view";
    }
}
