package com.shopme.admin.brand.controllers;

import com.shopme.admin.brand.services.BrandService;
import com.shopme.admin.category.services.CategoryService;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

}
