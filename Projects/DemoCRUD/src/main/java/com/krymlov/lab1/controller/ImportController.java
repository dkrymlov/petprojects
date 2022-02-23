package com.krymlov.lab1.controller;

import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.service.CategoryService;
import com.krymlov.lab1.service.ImportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/category")
    public String uploadExcel(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importCategory(reapExcelDataFile) != null){
            redirectAttributes.addFlashAttribute("wrongData", importService.importCategory(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/brand")
    public String uploadBrand(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importBrand(reapExcelDataFile) != null){
            redirectAttributes.addFlashAttribute("wrongData", importService.importBrand(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/seller")
    public String uploadSeller(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importSeller(reapExcelDataFile) != null){
            redirectAttributes.addFlashAttribute("wrongData", importService.importSeller(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/item")
    public String uploadItem(@RequestParam("file") MultipartFile reapExcelDataFile,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importItem(reapExcelDataFile) != null){
            redirectAttributes.addFlashAttribute("wrongData", importService.importItem(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/country")
    public String uploadCountry(@RequestParam("file") MultipartFile reapExcelDataFile,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");

        if (importService.importCountry(reapExcelDataFile) != null){
            redirectAttributes.addFlashAttribute("wrongData", importService.importCountry(reapExcelDataFile));
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

}
