package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.*;
import com.krymlov.lab1.export.*;
import com.krymlov.lab1.repository.CountryRepo;
import com.krymlov.lab1.service.BrandService;
import com.krymlov.lab1.service.CategoryService;
import com.krymlov.lab1.service.ItemService;
import com.krymlov.lab1.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/export/brand")
    public void getBrandData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BrandsData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<BrandEntity> brands = brandService.getBrandRepo().findAll();
        List<BrandEntity> listBrands = new ArrayList<>();
        for (BrandEntity brand : brands){
            listBrands.add(brand);
        }
        BrandDataExcelExporter excelExporter = new BrandDataExcelExporter(listBrands);
        excelExporter.export(response);
    }

    @RequestMapping("/export/category")
    public void getCategoriesData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CategoriesData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<CategoryEntity> categories = categoryService.getCategoryRepo().findAll();
        List<CategoryEntity> listCategories = new ArrayList<>();
        for (CategoryEntity category : categories){
            listCategories.add(category);
        }
        CategoryDataExcelExporter excelExporter = new CategoryDataExcelExporter(listCategories);

        excelExporter.export(response);
    }

    @RequestMapping("/export/seller")
    public void getSellersData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=SellersData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<SellerEntity> sellers = sellerService.getSellerRepo().findAll();
        List<SellerEntity> listSellers = new ArrayList<>();
        for (SellerEntity seller : sellers){
            listSellers.add(seller);
        }
        SellerDataExcelExporter excelExporter = new SellerDataExcelExporter(listSellers);

        excelExporter.export(response);
    }

    @RequestMapping("/export/country")
    public void getCountriesData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CountriesData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<CountryEntity> countries = countryRepo.findAll();
        List<CountryEntity> listCountries = new ArrayList<>();
        for (CountryEntity country : countries){
            listCountries.add(country);
        }
        CountryDataExcelExport excelExporter = new CountryDataExcelExport(listCountries);

        excelExporter.export(response);
    }

    @RequestMapping("/export/item")
    public void getItemsData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ItemsData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<ItemEntity> items = itemService.getItemRepo().findAll();
        List<ItemEntity> listItems = new ArrayList<>();
        for (ItemEntity item : items){
            listItems.add(item);
        }
        ItemDataExcelExporter excelExporter = new ItemDataExcelExporter(listItems);

        excelExporter.export(response);
    }
}
