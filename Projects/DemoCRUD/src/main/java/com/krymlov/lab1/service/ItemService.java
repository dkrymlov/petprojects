package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Item;
import com.krymlov.lab1.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public ItemRepo getItemRepo() {
        return itemRepo;
    }

    public CategoryRepo getCategoryRepo() {
        return categoryRepo;
    }

    public BrandRepo getBrandRepo() {
        return brandRepo;
    }

    public SellerRepo getSellerRepo() {
        return sellerRepo;
    }

    public CartItemRepo getCartItemRepo() {
        return cartItemRepo;
    }

    private String checks(Item item){
        if (itemRepo.findByNameAndInfoAndCategoryIdAndBrandIdAndSellerId(item.getName(),item.getInfo(), item.getCategory().getId(),item.getBrand().getId(),item.getSeller().getId()) != null){
            ItemEntity itemEntity = itemRepo.findByNameAndInfoAndCategoryIdAndBrandIdAndSellerId
                    (item.getName(),item.getInfo(), item.getCategory().getId(),item.getBrand().getId(), item.getSeller().getId());
            if (itemEntity.getSeller().getId().equals(item.getSeller().getId())){
                return "У однакових товарів повинен бути хоча б інший продавець.";
            }
        }
        if (item.getPrice() <= 0){
            return "Ціна повинна бути більше нуля!";
        }
        return null;
    }

    public String checkItemCreate(Item item){

        if (item.getBrand() != null && item.getCategory() != null && item.getSeller() != null){

            if (checks(item) != null){
                return checks(item);
            }else {
                CategoryEntity categoryEntity = categoryRepo.findById(item.getCategory().getId()).get();
                BrandEntity brandEntity = brandRepo.findById(item.getBrand().getId()).get();
                SellerEntity sellerEntity = sellerRepo.findById(item.getSeller().getId()).get();

                ItemEntity itemEntity =
                        new ItemEntity(item.getName(), item.getInfo(), categoryEntity, brandEntity, sellerEntity, item.getPrice());

                itemRepo.save(itemEntity);

                return null;
            }
        }else return "Ви ввели неправильний ідентифікатор для категорії/бреду/продавця";
    }

    public String checkItemEdit(Item item){

        if (item.getBrand() != null && item.getCategory() != null && item.getSeller() != null){

            if (checks(item) != null){
                return checks(item);
            }else{
                ItemEntity itemEntity = itemRepo.findById(item.getId()).get();
                ItemEntity returnEntity =
                        new ItemEntity(item.getName(), item.getInfo(), item.getCategory(), item.getBrand(), item.getSeller(), item.getPrice());
                BeanUtils.copyProperties(returnEntity, itemEntity, "id");
                itemRepo.save(itemEntity);
                return null;
            }

        }else return "Ви ввели неправильний ідентифікатор для категорії/бреду/продавця";
    }
}
