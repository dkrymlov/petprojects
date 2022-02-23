package com.krymlov.lab1.repository;

import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends CrudRepository<ItemEntity, Long> {
    Iterable<ItemEntity> findAllByCategoryId(Long id);
    Iterable<ItemEntity> findAllByBrandId(Long id);
    Iterable<ItemEntity> findAllBySellerId(Long id);
    Iterable<ItemEntity> deleteAllBySellerId(Long id);
    Iterable<ItemEntity> deleteAllByCategory_Id(Long id);
    Iterable<ItemEntity> deleteAllByBrandId(Long id);
    Iterable<ItemEntity> findByOrderByPriceAsc();
    Iterable<ItemEntity> findByOrderByPriceDesc();
    ItemEntity findByName(String name);
    ItemEntity findByNameAndInfoAndCategoryIdAndBrandIdAndSellerId(String name, String info, Long category_id, Long brand_id, Long seller_id);
    int countAllByBrandId(Long id);
    int countAllByBrandIdAndCategoryId(Long brand_id, Long category_id);
    int countAllByBrandIdAndSellerId(Long brand_id, Long seller_id);
    int countAllByCategoryIdAndSellerId(Long category_id, Long seller_id);
    int countAllByCategoryId(Long id);
    int countAllBySellerId(Long id);
    int countAllBySellerIdAndCategoryId(Long seller_id, Long category_id);
    boolean existsById(Long id);
}
