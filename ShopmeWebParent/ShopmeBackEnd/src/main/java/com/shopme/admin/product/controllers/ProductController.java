package com.shopme.admin.product.controllers;

import com.shopme.admin.product.services.ProductService;
import com.shopme.common.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {

        List<Product> listProducts = productService.listAll();

        model.addAttribute("listProducts",  listProducts);

        return "products/index";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("product", new Product());
        model.addAttribute("listProducts",  listProducts);
        model.addAttribute("pageTitle", "إضافة منتج جديد");
        return "products/product_form";
    }

}
