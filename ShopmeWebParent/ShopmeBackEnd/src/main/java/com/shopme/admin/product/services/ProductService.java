package com.shopme.admin.product.services;

import com.shopme.admin.product.repositories.ProductRepository;
import com.shopme.common.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }


}
