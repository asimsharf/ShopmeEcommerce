package com.shopme.admin.brand.apis;

import com.shopme.admin.brand.dto.CategoryDTO;
import com.shopme.admin.brand.exceptions.BrandNotFoundException;
import com.shopme.admin.brand.exceptions.BrandNotFoundRestException;
import com.shopme.admin.brand.services.BrandService;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RestBrandController {

    private final BrandService service;

    public RestBrandController(BrandService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/brands/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
        return service.checkUnique(id, name);
    }

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable("id") Integer brandId) throws BrandNotFoundRestException {
        List<CategoryDTO> listCategories = new ArrayList<>();
       try{
           Brand brand = service.get(brandId);
           Set<Category> categories = brand.getCategories();
           for (Category category : categories) {
               CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
               listCategories.add(dto);
           }
           return listCategories;
       } catch (BrandNotFoundException ex) {
           throw new BrandNotFoundRestException("لم يتم العثو على ماركة بالمعرف " + brandId);
       }
    }

}
