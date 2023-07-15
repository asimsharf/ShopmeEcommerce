package com.shopme.admin.brand.services;

import com.shopme.admin.brand.repositories.BrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandRepository repo;


}
