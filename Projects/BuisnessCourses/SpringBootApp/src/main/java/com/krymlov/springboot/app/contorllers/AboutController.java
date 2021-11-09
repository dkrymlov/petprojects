package com.krymlov.springboot.app.contorllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        model.addAttribute("about", "We are leading specialists in the field of " +
                "computer technologies. We have been in the labor market for almost 10 years");
        model.addAttribute("about2",  "and during this time we have graduated many specialists.");
        model.addAttribute("text", "Each of the bottom now earns at least " +
                "$1,500 a month. Quite a bit, right?");
        model.addAttribute("invitation", "And our courses cost only $500, so don't waste time and sign up!");
        return "about";
    }
}
