package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.model.Brand;
import com.krymlov.lab1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public SellerRepo getSellerRepo() {
        return sellerRepo;
    }

    public CategoryRepo getCategoryRepo() {
        return categoryRepo;
    }

    public BrandRepo getBrandRepo() {
        return brandRepo;
    }

    public ItemRepo getItemRepo() {
        return itemRepo;
    }

    public CountryRepo getCountryRepo() {
        return countryRepo;
    }

    public CartItemRepo getCartItemRepo() {
        return cartItemRepo;
    }

    public String createBrandCheck(Brand brand) {
        if (brandRepo.findByName(brand.getName()) != null) {
            return "Неможливо створити ще один бренд з такою назвою.";
        }
        if (brand.getCountry() == null) {
            return "Неправильний ідентифікатор країни.";
        }
        return null;
    }

    public String editBrandCheck(Brand brand) {

        BrandEntity temp = brandRepo.findById(brand.getId()).get();
        if (temp.getName().equals(brand.getName())) {

            if (brand.getCountry() == null) {
                return "Неправильний ідентифікатор країни.";
            }

            return null;
        }

        if (brandRepo.findByName(brand.getName()) != null) {
            return "Неможливо створити ще один бренд з такою назвою";
        }

        return null;
    }
}
