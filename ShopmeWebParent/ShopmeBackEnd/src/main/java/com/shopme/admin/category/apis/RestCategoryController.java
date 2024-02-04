package com.shopme.admin.category.apis;

import com.shopme.admin.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestCategoryController {

    private final CategoryService service;

    public RestCategoryController(CategoryService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/categories/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
        return service.checkUnique(id, name, alias);
    }

}
