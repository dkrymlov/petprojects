package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.CartItemEntity;
import com.krymlov.lab1.repository.CartItemRepo;
import com.krymlov.lab1.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public ItemRepo getItemRepo() {
        return itemRepo;
    }

    public CartItemRepo getCartItemRepo() {
        return cartItemRepo;
    }

    public List<String> getItems(Iterable<CartItemEntity> cartItems){
        List<String> items = new ArrayList<>();
        for (CartItemEntity cartItem : cartItems){
            items.add(cartItem.getItem().getId() + " " +
                    cartItem.getItem().getName() + " " +
                    cartItem.getItem().getBrand().getName() + " " +
                    cartItem.getItem().getSeller().getName());
        }
        return items;
    }

    public int getTotalCartPrice(Iterable<CartItemEntity> cartItems){
        int totalPrice = 0;
        for (CartItemEntity cartItem : cartItems){
            totalPrice += cartItem.getItem().getPrice();
        }
        return totalPrice;
    }
}
