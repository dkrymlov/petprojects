package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Category;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.service.CategoryService;
import com.krymlov.lab1.service.GoogleChartsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoogleChartsUtils gcu;


    @RequestMapping("/category")
    public String getCategories(Model model){

        Iterable<CategoryEntity> categories = categoryService.getCategoryRepo().findAll();
        model.addAttribute("categories", categories);

        return "categories/category";
    }

    @RequestMapping("/category/create")
    public String getCreateCategory(Model model){
        return "/categories/create-category";
    }

    @PostMapping("/category/create")
    public String createCategory(@Valid Category category, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");

        if (categoryService.createCategoryCheck(category) != null){
            redirectAttributes.addFlashAttribute("wrongData", categoryService.createCategoryCheck(category));
            return "redirect:"+ referer;
        }

        CategoryEntity categoryEntity = new CategoryEntity(category.getName(), category.getInfo());
        categoryService.getCategoryRepo().save(categoryEntity);

        return "redirect:/category";
    }

    @RequestMapping("/category/edit")
    public String getEditCategory(@Valid Category category, Model model) {

        model.addAttribute("id", category.getId());
        model.addAttribute("name", category.getName());
        model.addAttribute("info", category.getInfo());

        return "/categories/edit-category";
    }

    @PostMapping("/category/edit")
    public String editCategory(@Valid Category category, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");

        if (categoryService.getCategoryRepo().findByName(category.getName()) != null){
            redirectAttributes.addFlashAttribute("wrongData", "Неможливо створити однакову категорію.");
            return "redirect:"+ referer;
        }

        CategoryEntity categoryEntity = categoryService.getCategoryRepo().findById(category.getId()).get();
        CategoryEntity returnEntity = new CategoryEntity(category.getName(), category.getInfo());

        BeanUtils.copyProperties(returnEntity, categoryEntity, "id");

        categoryService.getCategoryRepo().save(categoryEntity);

        return "redirect:/category";
    }

    @RequestMapping("/category/delete")
    public String getDeleteCategory(@Valid Category category, Model model) {

        model.addAttribute("id", category.getId());
        model.addAttribute("name", category.getName());
        model.addAttribute("info", category.getInfo());

        return "/categories/delete-category";
    }

    @Transactional
    @PostMapping("/category/delete")
    public String deleteCategory(@Valid Category category, RedirectAttributes redirectAttributes){

        CategoryEntity categoryEntity = categoryService.getCategoryRepo().findById(category.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Категорію з назвою " + categoryEntity.getName() + " було видалено.");

        Iterable<ItemEntity> items = categoryService.getItemRepo().findAllByCategoryId(category.getId());
        for(ItemEntity item : items){
            categoryService.getCartItemRepo().deleteByItemId(item.getId());
        }

        categoryService.getItemRepo().deleteAllByCategory_Id(category.getId());
        categoryService.getCategoryRepo().deleteById(category.getId());

        return "redirect:/category";
    }

    @RequestMapping("/category/details")
    public String getDetailsCategory(@Valid Category category, Model model){

        Iterable<BrandEntity> brands = categoryService.getBrandRepo().findAll();
        Iterable<SellerEntity> sellers = categoryService.getSellerRepo().findAll();
        Iterable<ItemEntity> items = categoryService.getItemRepo().findAllByCategoryId(category.getId());

        model.addAttribute("brandsData", gcu.getCategoryBrandsMap(brands, category));
        model.addAttribute("sellersData", gcu.getCategorySellersMap(sellers, category));

        model.addAttribute("name", category.getName());
        model.addAttribute("info", category.getInfo());
        model.addAttribute("items", items);

        return "/categories/details-category";
    }



}
