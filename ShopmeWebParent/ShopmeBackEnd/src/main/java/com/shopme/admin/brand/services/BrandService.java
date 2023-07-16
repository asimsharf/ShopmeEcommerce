package com.shopme.admin.brand.services;


import java.util.List;

import com.shopme.admin.brand.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Brand;

@Service
public class BrandService {
    public static final int BRANDS_PER_PAGE = 10;

    @Autowired
    private BrandRepository repo;

    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {

        helper.listEntities(pageNum, BRANDS_PER_PAGE, repo);

    }

}