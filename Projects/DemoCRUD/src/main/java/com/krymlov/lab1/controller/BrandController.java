package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.*;
import com.krymlov.lab1.model.Brand;
import com.krymlov.lab1.service.BrandService;
import com.krymlov.lab1.service.GoogleChartsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private GoogleChartsUtils gcu;

    @RequestMapping("/brand")
    public String getBrands(Model model, RedirectAttributes redirectAttributes){

        Iterable<BrandEntity> brands = brandService.getBrandRepo().findAll();

        model.addAttribute("brands", brands);

        return "/brands/brand";
    }

    @RequestMapping("/brand/create")
    public String getCreateBrand(Model model){
            Iterable<CountryEntity> countries = brandService.getCountryRepo().findAll();
            model.addAttribute("countries", countries);
            return "/brands/create-brand";
    }

    @PostMapping("/brand/create")
    public String createBrand(@Valid Brand brand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

            String referer = request.getHeader("Referer");

            if (brandService.createBrandCheck(brand) != null){
                redirectAttributes.addFlashAttribute("wrongData", brandService.createBrandCheck(brand));
                return "redirect:" + referer;
            }

            CountryEntity countryEntity = brandService.getCountryRepo().findById(brand.getCountry().getId()).get();
            BrandEntity brandEntity = new BrandEntity(brand.getName(),brand.getInfo(),countryEntity);
            brandService.getBrandRepo().save(brandEntity);

            return "redirect:/brand";
    }

    @PostMapping("/brand/edit")
    public String editBrand(@Valid Brand brand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");

        if (brandService.editBrandCheck(brand) != null){
            redirectAttributes.addFlashAttribute("wrongData", brandService.editBrandCheck(brand));
            return "redirect:" + referer;
        }

        BrandEntity brandEntity = brandService.getBrandRepo().findById(brand.getId()).get();
        BrandEntity returnEntity = new BrandEntity(brand.getName(), brand.getInfo(), brand.getCountry());
        BeanUtils.copyProperties(returnEntity, brandEntity, "id");
        brandService.getBrandRepo().save(brandEntity);

        return "redirect:/brand";
    }

    @RequestMapping("/brand/edit")
    public String getEditBrand(@Valid Brand brand, Model model){
        Iterable<CountryEntity> countries = brandService.getCountryRepo().findAll();
        model.addAttribute("id", brand.getId());
        model.addAttribute("name", brand.getName());
        model.addAttribute("info", brand.getInfo());
        model.addAttribute("country", brand.getCountry());
        model.addAttribute("countries", countries);

        return "/brands/edit-brand";
    }

    @Transactional
    @PostMapping("/brand/delete")
    public String deleteBrand(@Valid Brand brand, Model model, RedirectAttributes redirectAttributes){

        BrandEntity brandEntity = brandService.getBrandRepo().findById(brand.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Бренд з назвою " + brandEntity.getName() + " було видалено.");
        Iterable<ItemEntity> items = brandService.getItemRepo().findAllByBrandId(brand.getId());
        for(ItemEntity item : items){
            brandService.getCartItemRepo().deleteByItemId(item.getId());
        }
        brandService.getItemRepo().deleteAllByBrandId(brand.getId());
        brandService.getBrandRepo().deleteById(brand.getId());

        return "redirect:/brand";
    }

    @RequestMapping("/brand/delete")
    public String getDeleteBrand(@Valid Brand brand, Model model){

        model.addAttribute("id", brand.getId());
        model.addAttribute("name", brand.getName());
        model.addAttribute("info", brand.getInfo());
        model.addAttribute("country", brand.getCountry());

        return "/brands/delete-brand";
    }

    @RequestMapping("/brand/details")
    public String getDetailsBrand(@Valid Brand brand, Model model){

        Iterable<SellerEntity> sellers = brandService.getSellerRepo().findAll();
        Iterable<CategoryEntity> categories = brandService.getCategoryRepo().findAll();
        Iterable<ItemEntity> items = brandService.getItemRepo().findAllByBrandId(brand.getId());

        model.addAttribute("sellersData", gcu.getBrandSellersMap(sellers, brand));
        model.addAttribute("categoriesData", gcu.getBrandCategoriesMap(categories, brand));

        model.addAttribute("name", brand.getName());
        model.addAttribute("info", brand.getInfo());
        model.addAttribute("country", brand.getCountry().getName());
        model.addAttribute("items", items);

        return "/brands/details-brand";
    }


}
