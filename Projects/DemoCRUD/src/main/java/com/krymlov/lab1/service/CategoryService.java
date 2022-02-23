package com.krymlov.lab1.service;

import com.krymlov.lab1.model.Category;
import com.krymlov.lab1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public BrandRepo getBrandRepo() {
        return brandRepo;
    }

    public SellerRepo getSellerRepo() {
        return sellerRepo;
    }

    public CategoryRepo getCategoryRepo() {
        return categoryRepo;
    }

    public ItemRepo getItemRepo() {
        return itemRepo;
    }

    public CartItemRepo getCartItemRepo() {
        return cartItemRepo;
    }

    public String createCategoryCheck(Category category){
        if (categoryRepo.findByName(category.getName()) != null){
            return "Неможливо створити однакову категорію.";
        }
        return null;
    }
}
