package com.krymlov.lab1.service;

import com.krymlov.lab1.entity.*;
import com.krymlov.lab1.repository.CountryRepo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {

    private final int CATEGORY_COLUMNS = 3;
    private final int BRAND_COLUMNS = 4;
    private final int COUNTRY_COLUMNS = 2;
    private final int SELLER_COLUMNS = 4;
    private final int ITEM_COLUMNS = 7;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ItemService itemService;

    public BrandService getBrandService() {
        return brandService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public CountryRepo getCountryRepo() {
        return countryRepo;
    }

    public SellerService getSellerService() {
        return sellerService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    private String checkEmpty(MultipartFile reapExcelDataFile){
        if (reapExcelDataFile.isEmpty()){
            return "Файл порожній!";
        }
        return null;
    }
    private String checkCells(int numberOfCells, int needed){
        if (numberOfCells != needed){
            return "Неправильна кількість колонок!";
        }
        return null;
    }

    public String importCategory(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<CategoryEntity> tempCategoryList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, CATEGORY_COLUMNS) != null){
            return checkCells(numberOfCells, CATEGORY_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            CategoryEntity tempCategory = new CategoryEntity();

            XSSFRow row = worksheet.getRow(i);

            tempCategory.setName(row.getCell(1).getStringCellValue());
            tempCategory.setInfo(row.getCell(2).getStringCellValue());

            if (categoryService.createCategoryCheck(tempCategory) == null){
                tempCategoryList.add(tempCategory);
            }

        }

        for (int i = 0; i < tempCategoryList.size(); i++) {

            categoryService.getCategoryRepo().save(tempCategoryList.get(i));

        }

        return null;
    }

    public String importBrand(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<BrandEntity> tempBrandList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, BRAND_COLUMNS) != null){
            return checkCells(numberOfCells, BRAND_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            BrandEntity tempBrand = new BrandEntity();

            XSSFRow row = worksheet.getRow(i);

            tempBrand.setName(row.getCell(1).getStringCellValue());
            tempBrand.setInfo(row.getCell(2).getStringCellValue());
            tempBrand.setCountry(countryRepo.findCountryEntityByName(row.getCell(3).getStringCellValue()));

            if (brandService.createBrandCheck(tempBrand) == null){
                tempBrandList.add(tempBrand);
            }

        }

        for (int i = 0; i < tempBrandList.size(); i++) {

            brandService.getBrandRepo().save(tempBrandList.get(i));

        }

        return null;
    }

    public String importCountry(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<CountryEntity> tempCountryList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, COUNTRY_COLUMNS) != null){
            return checkCells(numberOfCells, COUNTRY_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            CountryEntity tempCountry = new CountryEntity();

            XSSFRow row = worksheet.getRow(i);

            tempCountry.setName(row.getCell(1).getStringCellValue());

            if (!countryRepo.existsByName(row.getCell(1).getStringCellValue())){
                tempCountryList.add(tempCountry);
            }

        }

        for (int i = 0; i < tempCountryList.size(); i++) {

            countryRepo.save(tempCountryList.get(i));

        }

        return null;
    }

    public String importSeller(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<SellerEntity> tempSellerList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, SELLER_COLUMNS) != null){
            return checkCells(numberOfCells, SELLER_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            SellerEntity tempSeller = new SellerEntity();

            XSSFRow row = worksheet.getRow(i);

            tempSeller.setName(row.getCell(1).getStringCellValue());
            tempSeller.setInfo(row.getCell(2).getStringCellValue());
            tempSeller.setAccreditation((long) row.getCell(3).getNumericCellValue());

            if (sellerService.checkSellerCreate(tempSeller) == null){
                tempSellerList.add(tempSeller);
            }

        }

        for (int i = 0; i < tempSellerList.size(); i++) {

            sellerService.getSellerRepo().save(tempSellerList.get(i));

        }

        return null;
    }

    public String importItem(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<ItemEntity> tempItemList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        int numberOfCells = worksheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, ITEM_COLUMNS) != null){
            return checkCells(numberOfCells, ITEM_COLUMNS);
        }

        for(int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            ItemEntity tempItem = new ItemEntity();

            XSSFRow row = worksheet.getRow(i);

            tempItem.setName(row.getCell(1).getStringCellValue());
            tempItem.setInfo(row.getCell(2).getStringCellValue());
            tempItem.setPrice((int) row.getCell(3).getNumericCellValue());
            tempItem.setBrand(itemService.getBrandRepo().findByName(row.getCell(4).getStringCellValue()));
            tempItem.setCategory(itemService.getCategoryRepo().findByName(row.getCell(5).getStringCellValue()));
            tempItem.setSeller(itemService.getSellerRepo().findByName(row.getCell(6).getStringCellValue()));

            if (itemService.checkItemCreate(tempItem) == null){
                tempItemList.add(tempItem);
            }

        }

        return null;
    }

}
