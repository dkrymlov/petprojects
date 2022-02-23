package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.CartItem;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class CartItemEntity extends CartItem {

    public CartItemEntity() {
        super();
    }

    public CartItemEntity(@NotNull @NotEmpty ItemEntity item) {
        super(item);
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

    @OneToOne
    @Override
    public ItemEntity getItem() {
        return super.getItem();
    }

    @Override
    public void setItem(ItemEntity item) {
        super.setItem(item);
    }
}
