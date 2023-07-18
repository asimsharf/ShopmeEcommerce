package com.shopme.admin.brand.controllers;

import com.shopme.admin.brand.exceptions.BrandNotFoundException;
import com.shopme.admin.brand.services.BrandService;
import com.shopme.admin.category.services.CategoryService;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
public class BrandController {

    private String defaultRedirectURL = "redirect:/brands/page/1?sortField=name&sortDir=asc";
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/brands")
    public String listFirstPage() {
        return defaultRedirectURL;
    }

    @GetMapping("/brands/page/{pageNum}")
    public String listByPage(@PagingAndSortingParam(listName = "listBrands", moduleURL = "/brands") PagingAndSortingHelper helper, @PathVariable(name = "pageNum") int pageNum) {
        brandService.listByPage(pageNum, helper);
        return "brands/index";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create New Brand");

        return "brands/brand_form";
    }

    @PostMapping("/brands/save")
    public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            brand.setImage(fileName);

            Brand savedBrand = brandService.save(brand);
            String uploadDir = "brand-image/" + savedBrand.getId();

            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            brandService.save(brand);
        }

        ra.addFlashAttribute("message", "The Brand has been saved successfully.");
        return "redirect:/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
        try {

            Brand brand = brandService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("brand", brand);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Brand (ID: " + id + ")");

            return "brands/brand_form";

        } catch (BrandNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
        try {
            brandService.delete(id);
            String brandDir = "brand-image/" + id;
            FileUploadUtil.removeDir(brandDir);

            ra.addFlashAttribute("message", "The brand ID " + id + " has been deleted successfully");
        } catch (BrandNotFoundException | IOException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/brands";
    }

}
