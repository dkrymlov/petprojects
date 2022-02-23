package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.SellerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends CrudRepository<SellerEntity, Long> {
    SellerEntity findByName(String name);
    SellerEntity findByAccreditation(Long accreditation);
}
