package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.model.Seller;
import com.krymlov.lab1.service.GoogleChartsUtils;
import com.krymlov.lab1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private GoogleChartsUtils gcu;

    @RequestMapping("/seller")
    public String getSellers(Model model){

        Iterable<SellerEntity> sellers = sellerService.getSellerRepo().findAll();
        model.addAttribute("sellers", sellers);

        return "/sellers/seller";
    }

    @RequestMapping("/seller/create")
    public String getCreateSeller(Model model){
        return "/sellers/create-seller";
    }

    @PostMapping("/seller/create")
    public String createSeller(@Valid Seller seller, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        String referer = request.getHeader("Referer");

        if (sellerService.checkSellerCreate(seller) != null){
            redirectAttributes.addFlashAttribute("wrongData", sellerService.checkSellerCreate(seller));
            return "redirect:" + referer;
        }

        SellerEntity sellerEntity = new SellerEntity(seller.getName(),seller.getInfo(), seller.getAccreditation());
        sellerService.getSellerRepo().save(sellerEntity);

        return "redirect:/seller";
    }

    @PostMapping("/seller/edit")
    public String editSeller(@Valid Seller seller, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){

        SellerEntity sellerEntity = sellerService.getSellerRepo().findById(seller.getId()).get();
        SellerEntity returnEntity = new SellerEntity(seller.getName(), seller.getInfo(), seller.getAccreditation());
        String referer = request.getHeader("Referer");

        SellerEntity temp = sellerService.getSellerRepo().findById(seller.getId()).get();

        if (sellerService.checkSellerEdit(seller, temp, sellerEntity, returnEntity) != null){
            redirectAttributes.addFlashAttribute("wrongData", sellerService.checkSellerEdit(seller, temp, sellerEntity, returnEntity));
            return "redirect:" + referer;
        }else return "redirect:/seller";

    }

    @RequestMapping("/seller/edit")
    public String getEditSeller(@Valid Seller seller, Model model){

        model.addAttribute("id", seller.getId());
        model.addAttribute("name", seller.getName());
        model.addAttribute("info", seller.getInfo());
        model.addAttribute("accreditation", seller.getAccreditation());

        return "/sellers/edit-seller";
    }

    @Transactional
    @PostMapping("/seller/delete")
    public String deleteSeller(@Valid Seller seller, RedirectAttributes redirectAttributes){

        SellerEntity sellerEntity = sellerService.getSellerRepo().findById(seller.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Продавця з назвою " + sellerEntity.getName() + " було видалено.");

        Iterable<ItemEntity> items = sellerService.getItemRepo().findAllBySellerId(seller.getId());
        for (ItemEntity item : items){
            sellerService.getCartItemRepo().deleteByItemId(item.getId());
        }

        sellerService.getItemRepo().deleteAllBySellerId(seller.getId());
        sellerService.getSellerRepo().deleteById(seller.getId());

        return "redirect:/seller";
    }

    @RequestMapping("/seller/delete")
    public String getDeleteSeller(@Valid Seller seller, Model model){

        model.addAttribute("id", seller.getId());
        model.addAttribute("name", seller.getName());
        model.addAttribute("info", seller.getInfo());
        model.addAttribute("accreditation", seller.getAccreditation());

        return "/sellers/delete-seller";
    }

    @RequestMapping("/seller/details")
    public String getDetailsSeller(@Valid Seller seller, Model model){

        Iterable<BrandEntity> brands = sellerService.getBrandRepo().findAll();
        Iterable<CategoryEntity> categories = sellerService.getCategoryRepo().findAll();
        Iterable<ItemEntity> items = sellerService.getItemRepo().findAllBySellerId(seller.getId());

        model.addAttribute("brandsData", gcu.getSellerBrandsMap(brands, seller));
        model.addAttribute("categoriesData", gcu.getSellerCategoriesMap(categories, seller));

        model.addAttribute("name", seller.getName());
        model.addAttribute("info", seller.getInfo());
        model.addAttribute("accreditation", seller.getAccreditation());
        model.addAttribute("items", items);

        return "/sellers/details-seller";
    }

}
