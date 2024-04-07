package com.shopme.admin.brand.services;

import com.shopme.admin.brand.exceptions.BrandNotFoundException;
import com.shopme.admin.brand.repositories.BrandRepository;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Brand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BrandService {
    public static final int BRANDS_PER_PAGE = 10;

    private final BrandRepository repo;

    public BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {

        helper.listEntities(pageNum, BRANDS_PER_PAGE, repo);

    }

    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    public Brand get(Integer id) throws BrandNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BrandNotFoundException("Could not find any brands with ID " + id);
        }
    }

    public void delete(Integer id) throws BrandNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID " + id);
        }

        repo.deleteById(id);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Brand brandName = repo.findByName(name);

        if (isCreatingNew) {
            if (brandName != null) {
                return "DuplicateName";
            }
        } else {
            if (brandName != null && !Objects.equals(brandName.getId(), id)) {
                return "DuplicateName";
            }
        }

        return "OK";
    }

}