package com.shopme.admin.product.controllers;

import com.shopme.admin.brand.services.BrandService;
import com.shopme.admin.product.exceptions.ProductNotFoundException;
import com.shopme.admin.product.services.ProductService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;

    public ProductController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "products/index";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {

        List<Brand> listBrands = brandService.listAll();
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", listBrands);
        model.addAttribute("pageTitle", "إضافة منتج جديد");

        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes ra, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            product.setMainImage(fileName);
            Product savedProduct = productService.save(product);
            String uploadDir = "product-images/" + savedProduct.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            productService.save(product);
        }
        ra.addFlashAttribute("message", "تم حفظ المنتج رقم " + product.getId() + " بنجاح");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        productService.updateProductEnabledStatus(id, enabled);
        String status = enabled ? "تم تفعيل" : "تم إلغاء التفعيل";
        String message = "المنتج رقم " + id + " تم " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }


    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes ra) throws ProductNotFoundException {
        productService.delete(id);
        String message = "تم حذف المنتج رقم " + id + " بنجاح";
        ra.addFlashAttribute("message", message);
        return "redirect:/products";
    }


}
