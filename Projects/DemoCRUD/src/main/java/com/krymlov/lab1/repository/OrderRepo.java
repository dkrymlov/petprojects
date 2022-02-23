package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<OrderEntity, Long> {
}
