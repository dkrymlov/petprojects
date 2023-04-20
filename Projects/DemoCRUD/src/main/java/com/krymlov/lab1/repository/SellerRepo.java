package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.SellerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends CrudRepository<SellerEntity, Long> {

    boolean existsByName(String name);
    int countAllByName(String name);
    SellerEntity findByName(String name);
    SellerEntity findByAccreditation(Long accreditation);

    SellerEntity getSellerEntityById(Long sellerId);
}
