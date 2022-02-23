package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Item;
import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.repository.*;
import com.krymlov.lab1.service.GoogleChartsUtils;
import com.krymlov.lab1.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private GoogleChartsUtils gcu;

    @RequestMapping("/item/create")
    public String getCreateItem(Model model){
        Iterable<BrandEntity> brands = itemService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = itemService.getCategoryRepo().findAll();
        Iterable<SellerEntity> sellers = itemService.getSellerRepo().findAll();

        model.addAttribute("brands",brands);
        model.addAttribute("categories",categories);
        model.addAttribute("sellers",sellers);

        return "/items/create-item";
    }

    @PostMapping("/item/create")
    public String createItem(@Valid Item item, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");

        if (itemService.checkItemCreate(item) != null){

            redirectAttributes.addFlashAttribute("wrongData", itemService.checkItemCreate(item));
            return "redirect:"+ referer;

        }else return "redirect:/item";

    }

    @RequestMapping("/item")
    public String getItems(Model model){

        Iterable<BrandEntity> brands = itemService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = itemService.getCategoryRepo().findAll();
        Iterable<SellerEntity> sellers = itemService.getSellerRepo().findAll();
        Iterable<ItemEntity> items = itemService.getItemRepo().findAll();

        model.addAttribute("brandsData", gcu.getItemBrandsMap(brands));
        model.addAttribute("categoriesData", gcu.getItemCategoriesMap(categories));
        model.addAttribute("sellersData", gcu.getItemSellersMap(sellers));
        model.addAttribute("items", items);

        return "items/item";
    }

    @RequestMapping("/item/sort/up")
    public String getSortUp(Model model){

        Iterable<BrandEntity> brands = itemService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = itemService.getCategoryRepo().findAll();
        Iterable<SellerEntity> sellers = itemService.getSellerRepo().findAll();
        Iterable<ItemEntity> items = itemService.getItemRepo().findByOrderByPriceAsc();

        model.addAttribute("brandsData", gcu.getItemBrandsMap(brands));
        model.addAttribute("categoriesData", gcu.getItemCategoriesMap(categories));
        model.addAttribute("sellersData", gcu.getItemSellersMap(sellers));
        model.addAttribute("items", items);

        return "items/item";
    }

    @RequestMapping("/item/sort/down")
    public String getSortDown(Model model){

        Iterable<BrandEntity> brands = itemService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = itemService.getCategoryRepo().findAll();
        Iterable<SellerEntity> sellers = itemService.getSellerRepo().findAll();
        Iterable<ItemEntity> items = itemService.getItemRepo().findByOrderByPriceDesc();

        model.addAttribute("brandsData", gcu.getItemBrandsMap(brands));
        model.addAttribute("categoriesData", gcu.getItemCategoriesMap(categories));
        model.addAttribute("sellersData", gcu.getItemSellersMap(sellers));
        model.addAttribute("items", items);

        return "items/item";
    }

    @PostMapping("/item/edit")
    public String editItem(@Valid Item item, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (itemService.checkItemEdit(item) != null){
            redirectAttributes.addFlashAttribute("wrongData", itemService.checkItemCreate(item));
            return "redirect:"+ referer;

        }else return "redirect:/item";
    }

    @RequestMapping("/item/edit")
    public String getEditItem(@Valid Item item, Model model){

        Iterable<BrandEntity> brands = itemService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = itemService.getCategoryRepo().findAll();
        Iterable<SellerEntity> sellers = itemService.getSellerRepo().findAll();

        model.addAttribute("brands",brands);
        model.addAttribute("categories",categories);
        model.addAttribute("sellers",sellers);

        model.addAttribute("id", item.getId());
        model.addAttribute("name", item.getName());
        model.addAttribute("info", item.getInfo());
        model.addAttribute("category", item.getCategory().getId());
        model.addAttribute("brand", item.getBrand().getId());
        model.addAttribute("seller", item.getSeller().getId());
        model.addAttribute("price", item.getPrice());

        return "/items/edit-item";
    }

    @Transactional
    @PostMapping("/item/delete")
    public String deleteItem(@Valid Item item, RedirectAttributes redirectAttributes){

        ItemEntity itemEntity = itemService.getItemRepo().findById(item.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Товар з назвою " + itemEntity.getName() + " було видалено.");

        itemService.getCartItemRepo().deleteAllByItem_Id(item.getId());

        itemService.getItemRepo().deleteById(item.getId());

        return "redirect:/item";
    }

    @RequestMapping("/item/delete")
    public String getDeleteItem(@Valid Item item, Model model){

        model.addAttribute("id", item.getId());
        model.addAttribute("name", item.getName());
        model.addAttribute("info", item.getInfo());
        model.addAttribute("category", item.getCategory().getName());
        model.addAttribute("brand", item.getBrand().getName());
        model.addAttribute("seller", item.getSeller().getName());
        model.addAttribute("price", item.getPrice());

        return "/items/delete-item";
    }


}
