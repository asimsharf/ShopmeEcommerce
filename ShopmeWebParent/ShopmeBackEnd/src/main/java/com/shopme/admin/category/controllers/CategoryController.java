package com.shopme.admin.category.controllers;

import com.shopme.admin.category.exceptions.CategoryNotFoundException;
import com.shopme.admin.category.services.CategoryService;
import com.shopme.admin.user.services.UserService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class CategoryController {

    @Autowired
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "id", "desc", null);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<Category> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Category> listCategories = page.getContent();

        long startCount = (long) (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endCount = startCount + UserService.USER_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "categories/index";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        model.addAttribute("pageTitle", "Create New Category");
        Category category = new Category();
        category.setEnabled(true);
        model.addAttribute("category", category);

        List<Category> listCategories = service.listCategoriesUsedInForm();
        model.addAttribute("listCategories", listCategories);

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, RedirectAttributes thRa, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            fileName = FileUploadUtil.renameFile(fileName);
            category.setImage(fileName);
            Category savedCategory = service.save(category);
            String uploadDir = "category-images/" + savedCategory.getId();

            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        if (category.getImage().isEmpty()) category.setImage(null);
        thRa.addFlashAttribute("message", "The user has been saved successfully.");
        service.save(category);


        return getRedirectURLtoAffectedUser(category);
    }

    private String getRedirectURLtoAffectedUser(Category category) {
        String firstPartOfName = category.getName().split(" ")[0];
        return "redirect:/categories/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfName;
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Category category = service.get(id);
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");
        List<Category> listCategories = service.listCategoriesUsedInForm();
        model.addAttribute("listCategories", listCategories);
        return "categories/category_form";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes thRa) throws IOException {
        try {
            service.delete(id);
            FileUploadUtil.removeDir("category-images/" + id);

            thRa.addFlashAttribute("message", "The category ID " + id + " has been deleted successfully.");
        } catch (CategoryNotFoundException ex) {
            thRa.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        service.updateCategoryEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The category ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/categories";
    }



}
