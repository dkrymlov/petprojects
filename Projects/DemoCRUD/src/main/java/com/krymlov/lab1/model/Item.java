package com.krymlov.lab1.model;


import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.entity.SellerEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Item {

    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String info;

    @NotEmpty
    @NotNull
    @Min(1)
    private CategoryEntity category;

    @NotEmpty
    @NotNull
    @Min(1)
    private BrandEntity brand;

    @NotEmpty
    @NotNull
    private SellerEntity seller;

    @NotEmpty
    @NotNull
    @Min(1)
    private Integer price;

    public Item() {}

    public Item(@NotEmpty @NotNull String name,
                @NotEmpty @NotNull String info,
                @NotEmpty @NotNull @Min(1) CategoryEntity category,
                @NotEmpty @NotNull @Min(1) BrandEntity brand,
                @NotEmpty @NotNull SellerEntity seller,
                @NotEmpty @NotNull @Min(1) Integer price) {
        this.name = name;
        this.info = info;
        this.category = category;
        this.brand = brand;
        this.seller = seller;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

   /* public void setCategoryName(String name){
        this.category.setName(name);
    }

    public String getCategoryName(){
        return category.getName();
    }

    public void setBrandName(String name){
        this.brand.setName(name);
    }

    public String getBrandName(){
        return brand.getName();
    }

    public void setSellerName(String name){
        this.seller.setName(name);
    }

    public String getSellerName(){
        return seller.getName();
    }*/

    public SellerEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
