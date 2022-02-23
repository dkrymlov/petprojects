package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.BrandEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepo extends CrudRepository<BrandEntity, Long> {
    BrandEntity findByName(String name);
}
