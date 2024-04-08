package com.shopme.admin.product.controllers;

import com.shopme.admin.brand.services.BrandService;
import com.shopme.admin.product.exceptions.ProductNotFoundException;
import com.shopme.admin.product.services.ProductService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Product;
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
    public String saveProduct(Product product, RedirectAttributes ra, @RequestParam("fileImage") MultipartFile mainImageMultipart, @RequestParam("extraFileImage") MultipartFile[] extraImageMultiparts) throws IOException {

        setMainImageName(mainImageMultipart, product);
        setExtraImageNames(extraImageMultiparts, product);
    
        Product savedProduct = productService.save(product);

        saveUploadedImages(extraImageMultiparts, savedProduct, mainImageMultipart);

        ra.addFlashAttribute("message", "تم حفظ المنتج رقم " + product.getId() + " بنجاح");
        return "redirect:/products";
    }

    private void setMainImageName(MultipartFile mainImageMultipart, Product product) {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = FileUploadUtil.renameFile(StringUtils.cleanPath(Objects.requireNonNull(mainImageMultipart.getOriginalFilename())));
            product.setMainImage(fileName);
        }
    }

    private void setExtraImageNames(MultipartFile[] extraImageMultiparts, Product product) {
        if (extraImageMultiparts.length > 0){
            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (!multipartFile.isEmpty()) {
                    String fileName = FileUploadUtil.renameFile(StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename())));
                    product.addExtraImage(fileName);
                }
            }
        }
    }

    private void saveUploadedImages(MultipartFile[] extraImageMultiparts, Product savedProduct, MultipartFile mainImageMultipart) throws IOException {

        if (!mainImageMultipart.isEmpty()) {
            String uploadDir = "product-images/" + savedProduct.getId();
            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, savedProduct.getMainImage(), mainImageMultipart);
        }

        if (extraImageMultiparts.length > 0) {
            String uploadDir = "product-images/" + savedProduct.getId() + "/extras";
            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);
            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (multipartFile.isEmpty()) continue;
                String fileName = FileUploadUtil.renameFile(StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename())));
                savedProduct.addExtraImage(fileName);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }

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
    public String deleteProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) throws ProductNotFoundException, IOException {
        try{
            productService.delete(id);

            String productExtraImagesDir = "product-images/" + id + "/extras";
            if (FileUploadUtil.isDirExists(productExtraImagesDir)) FileUploadUtil.removeDir(productExtraImagesDir);

            String productDir = "product-images/" + id;
            if (FileUploadUtil.isDirExists(productDir)) FileUploadUtil.removeDir(productDir);

            ra.addFlashAttribute("message", "تم حذف المنتج رقم " + id + " بنجاح");

        } catch (ProductNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/products";
    }


}
