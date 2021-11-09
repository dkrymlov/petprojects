package com.krymlov.springboot.app.contorllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    @GetMapping("/homepage")
    public String home(Model model) {
        model.addAttribute("title", "Homepage");
        model.addAttribute("welcome", "Welcome to our business school!");
        model.addAttribute("text", "We are cool businessmen and we want to teach you to make a lot of money.");
        model.addAttribute("invitation", "Sign up for our courses and study now.");
        return "home";
    }

}
