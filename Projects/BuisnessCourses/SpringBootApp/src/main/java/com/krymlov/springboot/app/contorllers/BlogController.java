package com.krymlov.springboot.app.contorllers;

import com.krymlov.springboot.app.models.Article;
import com.krymlov.springboot.app.repo.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private IArticleRepository articleRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("title", "Here is the list of active courses:");
        Iterable<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Add new article to our blog!");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogArticleAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String text, Model model){
        Article article = new Article(title, anons, text);
        articleRepository.save(article);
        return "redirect:/blog";
    }
}
