package com.krymlov.lab1.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Order {

    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private List<String> items;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    @Min(10)
    private Integer totalPrice;

    public Order(@NotEmpty @NotNull List<String> items,
                 @NotEmpty @NotNull String email,
                 @NotEmpty @NotNull @Min(1) Integer totalPrice) {
        this.items = items;
        this.email = email;
        this.totalPrice = totalPrice;
    }

    public Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
