package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Item;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ItemEntity extends Item {

    public ItemEntity() {
        super();
    }

    public ItemEntity(@NotEmpty @NotNull String name,
                      @NotEmpty @NotNull String info,
                      @NotEmpty @NotNull @Min(1) CategoryEntity category,
                      @NotEmpty @NotNull @Min(1) BrandEntity brand,
                      @NotEmpty @NotNull SellerEntity seller,
                      @NotEmpty @NotNull @Min(1) Integer price) {
        super(name, info, category, brand, seller, price);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(length = 50, nullable = false)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(nullable = false)
    @Override
    public String getInfo() {
        return super.getInfo();
    }

    @Override
    public void setInfo(String info) {
        super.setInfo(info);
    }

    @ManyToOne
    @Override
    public CategoryEntity getCategory() {
        return super.getCategory();
    }

    @Override
    public void setCategory(CategoryEntity category) {
        super.setCategory(category);
    }

    @ManyToOne
    @Override
    public BrandEntity getBrand() {
        return super.getBrand();
    }

    @Override
    public void setBrand(BrandEntity brand) {
        super.setBrand(brand);
    }

    @ManyToOne
    @Override
    public SellerEntity getSeller() {
        return super.getSeller();
    }

    @Override
    public void setSeller(SellerEntity seller) {
        super.setSeller(seller);
    }

    @Column(nullable = false)
    @Override
    public Integer getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(Integer price) {
        super.setPrice(price);
    }
}
