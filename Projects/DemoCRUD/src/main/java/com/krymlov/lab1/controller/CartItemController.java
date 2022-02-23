package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.CartItemEntity;
import com.krymlov.lab1.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/cart/add")
    public String getAddToCart(@RequestParam Long id, HttpServletRequest request){

        CartItemEntity cartItem = new CartItemEntity(cartItemService.getItemRepo().findById(id).get());
        cartItemService.getCartItemRepo().save(cartItem);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping("/cart/delete")
    public String getDeleteFromCart(@RequestParam Long id){

        cartItemService.getCartItemRepo().deleteById(id);

        return "redirect:/cart";
    }

    @RequestMapping("/cart")
    public String getCartItems(Model model){

        Iterable<CartItemEntity> cartItems = cartItemService.getCartItemRepo().findAll();

        model.addAttribute("items", cartItemService.getItems(cartItems));
        model.addAttribute("totalCartPrice", cartItemService.getTotalCartPrice(cartItems) + " грн.");
        model.addAttribute("totalPrice", cartItemService.getTotalCartPrice(cartItems));
        model.addAttribute("cartItems", cartItems);

        return "/cart/cart";
    }

    @RequestMapping("/cart/clean")
    public String getCleanCart(){

        cartItemService.getCartItemRepo().deleteAll();

        return "redirect:/cart";
    }
}
