package com.shopme.admin.product.apis;


import com.shopme.admin.product.services.ProductService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/products/check_unique")
    public String checkUnique(Integer id, String name, String alias) {
        return productService.checkUnique(id, name, alias);
    }

}
