package com.shopme.admin.product.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String listProducts(Model model) {

        model.addAttribute("message", "Welcome Product Page");

        return "products/index";
    }

}
