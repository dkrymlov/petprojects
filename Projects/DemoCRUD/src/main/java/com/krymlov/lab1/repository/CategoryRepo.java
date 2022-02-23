package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}
