package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.CartItemEntity;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends CrudRepository<CartItemEntity, Long> {
    void deleteAllByItem_Id(Long id);
    void deleteByItemId(Long id);
}
