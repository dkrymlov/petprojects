package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Seller;
import com.krymlov.lab1.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public ItemRepo getItemRepo() {
        return itemRepo;
    }

    public SellerRepo getSellerRepo() {
        return sellerRepo;
    }

    public BrandRepo getBrandRepo() {
        return brandRepo;
    }

    public CategoryRepo getCategoryRepo() {
        return categoryRepo;
    }

    public CartItemRepo getCartItemRepo() {
        return cartItemRepo;
    }

    public String checkSellerCreate(Seller seller){
        if (sellerRepo.findByName(seller.getName()) != null){
            return "Неможливо створити ще одного продавця з такою назвою.";
        }

        if (seller.getAccreditation() < 100000 || seller.getAccreditation() > 999999){ ;
            return "Ви ввели неправильний номер акредитації.";
        }

        if (sellerRepo.findByAccreditation(seller.getAccreditation()) != null){
            return "У всіх повинен бути унікальний номер акредитації.";
        }
        return null;
    }

    public String checkSellerEdit(Seller seller, SellerEntity temp, SellerEntity sellerEntity, SellerEntity returnEntity){

        if (temp.getName().equals(seller.getName())){

            if (seller.getAccreditation() < 100000 || seller.getAccreditation() > 999999){
                return "Ви ввели неправильний номер акредитації.";
            }

            if (!temp.getAccreditation().equals(seller.getAccreditation())){
                if (sellerRepo.findByAccreditation(seller.getAccreditation()) != null){
                    return "У всіх повинен бути унікальний номер акредитації.";
                }
            }

            BeanUtils.copyProperties(returnEntity, sellerEntity, "id");

            sellerRepo.save(sellerEntity);

            return null;
        }

        if (sellerRepo.findByName(seller.getName()) != null){
            return "Неможливо створити ще одного продавця з такою назвою.";
        }

        if (seller.getAccreditation() < 100000 || seller.getAccreditation() > 999999){
            return "Ви ввели неправильний номер акредитації.";
        }

        if (sellerRepo.findByAccreditation(seller.getAccreditation()) != null){
            return "У всіх повинен бути унікальний номер акредитації.";
        }

        BeanUtils.copyProperties(returnEntity, sellerEntity, "id");

        sellerRepo.save(sellerEntity);

        return null;
    }
}
