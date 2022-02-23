package com.krymlov.lab1.model;


import com.krymlov.lab1.entity.ItemEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CartItem {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private ItemEntity item;

    public CartItem(){}

    public CartItem(@NotNull @NotEmpty ItemEntity item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
