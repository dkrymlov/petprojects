package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Brand;
import com.krymlov.lab1.model.Category;
import com.krymlov.lab1.model.Seller;
import com.krymlov.lab1.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class GoogleChartsUtils {

    @Autowired
    private ItemRepo itemRepo;

    public Map<String, Integer> getItemBrandsMap(Iterable<BrandEntity> brands) {
        Map<String, Integer> brandsMap = new TreeMap<>();
        for (BrandEntity brand : brands) {
            brandsMap.put(brand.getName(), itemRepo.countAllByBrandId(brand.getId()));
        }
        return brandsMap;
    }

    public Map<String, Integer> getItemCategoriesMap(Iterable<CategoryEntity> categories) {
        Map<String, Integer> categoriesMap = new TreeMap<>();
        for (CategoryEntity category : categories) {
            categoriesMap.put(category.getName(), itemRepo.countAllByCategoryId(category.getId()));
        }
        return categoriesMap;
    }

    public Map<String, Integer> getItemSellersMap(Iterable<SellerEntity> sellers) {
        Map<String, Integer> sellersMap = new TreeMap<>();
        for (SellerEntity seller : sellers) {
            sellersMap.put(seller.getName(), itemRepo.countAllBySellerId(seller.getId()));
        }
        return sellersMap;
    }

    public Map<String, Integer> getBrandCategoriesMap(Iterable<CategoryEntity> categories, Brand brand) {
        Map<String, Integer> categoriesMap = new TreeMap<>();
        for (CategoryEntity category : categories) {
            categoriesMap.put(category.getName(), itemRepo.countAllByBrandIdAndCategoryId(brand.getId(), category.getId()));
        }
        return categoriesMap;
    }

    public Map<String, Integer> getBrandSellersMap(Iterable<SellerEntity> sellers, Brand brand) {
        Map<String, Integer> sellersMap = new TreeMap<>();
        for (SellerEntity seller : sellers) {
            sellersMap.put(seller.getName(), itemRepo.countAllByBrandIdAndSellerId(brand.getId(), seller.getId()));
        }
        return sellersMap;
    }

    public Map<String, Integer> getCategoryBrandsMap(Iterable<BrandEntity> brands, Category category) {
        Map<String, Integer> brandsMap = new TreeMap<>();
        for (BrandEntity brand : brands) {
            brandsMap.put(brand.getName(), itemRepo.countAllByBrandIdAndCategoryId(brand.getId(), category.getId()));
        }
        return brandsMap;
    }

    public Map<String, Integer> getCategorySellersMap(Iterable<SellerEntity> sellers, Category category) {
        Map<String, Integer> sellersMap = new TreeMap<>();
        for (SellerEntity seller : sellers) {
            sellersMap.put(seller.getName(), itemRepo.countAllBySellerIdAndCategoryId(seller.getId(), category.getId()));
        }
        return sellersMap;
    }

    public Map<String, Integer> getSellerBrandsMap(Iterable<BrandEntity> brands, Seller seller) {
        Map<String, Integer> brandsMap = new TreeMap<>();
        for (BrandEntity brand : brands) {
            brandsMap.put(brand.getName(), itemRepo.countAllByBrandIdAndSellerId(brand.getId(), seller.getId()));
        }
        return brandsMap;
    }

    public Map<String, Integer> getSellerCategoriesMap(Iterable<CategoryEntity> categories, Seller seller) {
        Map<String, Integer> categoriesMap = new TreeMap<>();
        for (CategoryEntity category : categories) {
            categoriesMap.put(category.getName(), itemRepo.countAllByCategoryIdAndSellerId(category.getId(), seller.getId()));
        }
        return categoriesMap;
    }

}
