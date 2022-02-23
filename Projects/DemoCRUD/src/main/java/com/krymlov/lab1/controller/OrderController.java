package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.OrderEntity;
import com.krymlov.lab1.model.Order;
import com.krymlov.lab1.repository.CartItemRepo;
import com.krymlov.lab1.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @PostMapping("/order/create")
    public String createOrder(@Valid Order order, RedirectAttributes redirectAttributes, HttpServletRequest request){

            String referer = request.getHeader("Referer");

            if (order.getTotalPrice().equals(0)){
                redirectAttributes
                        .addFlashAttribute("wrongData", "Додайте щось в корзину перед оформленням замовлення!");
                return "redirect:" + referer;
            }

            orderRepo.save(new OrderEntity(order.getItems(),order.getEmail(),order.getTotalPrice()));
            cartItemRepo.deleteAll();
            return "/orders/thanks";
    }

}
