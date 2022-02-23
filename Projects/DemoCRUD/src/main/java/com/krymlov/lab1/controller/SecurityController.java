package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.UserEntity;
import com.krymlov.lab1.model.RolesEnum;
import com.krymlov.lab1.model.User;
import com.krymlov.lab1.repository.UserRepo;
import com.krymlov.lab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getPage(){
        return "redirect:/category";
    }

    @RequestMapping("/registration")
    public String getRegisterUser(){
        return "security/register";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid User user, Model model, HttpServletRequest request){

        if (userService.registerNewAccount(user) != null){
            model.addAttribute("wrongData", userService.registerNewAccount(user));
            return "/security/register";
        }
        return "redirect:/login";
    }

    @RequestMapping("/access/denied")
    public String getAccessDeniedPage(HttpServletRequest request, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("forbiddenAccess", "У вас немає доступу до цієї сторінки!");

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
