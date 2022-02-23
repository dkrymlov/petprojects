package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Order;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class OrderEntity extends Order {

    public OrderEntity() {
        super();
    }

    public OrderEntity(@NotEmpty @NotNull List<String> items,
                       @NotEmpty @NotNull String email,
                       @NotEmpty @NotNull @Min(1) Integer totalPrice) {
        super(items, email, totalPrice);
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

    @ElementCollection
    @Override
    public List<String> getItems() {
        return super.getItems();
    }

    @Override
    public void setItems(List<String> items) {
        super.setItems(items);
    }

    @Column(nullable = false, length = 100)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Column(nullable = false)
    @Override
    public Integer getTotalPrice() {
        return super.getTotalPrice();
    }

    @Override
    public void setTotalPrice(Integer totalPrice) {
        super.setTotalPrice(totalPrice);
    }
}
