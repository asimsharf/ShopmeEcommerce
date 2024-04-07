package com.shopme.admin.product.services;

import com.shopme.admin.product.exceptions.ProductNotFoundException;
import com.shopme.admin.product.repositories.ProductRepository;
import com.shopme.common.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listAll() {

        return repo.findAll();
    }

    public Product save(Product product) {
        if (product.getId() == null){
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-").toLowerCase();
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-").toLowerCase());
        }
        product.setUpdatedTime(new Date());
        return repo.save(product);
    }

    public String checkUnique(Integer id, String name, String alias) {

        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = repo.findByName(name);
        if (isCreatingNew) {
            if (productByName != null) {
                return "DuplicateName";
            } else {
                Product productByAlias = repo.findByAlias(alias);
                if (productByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (productByName != null && !Objects.equals(productByName.getId(), id)) {
                return "DuplicateName";
            }

            Product productByAlias = repo.findByAlias(alias);
            if (productByAlias != null && !Objects.equals(productByAlias.getId(), id)) {
                return "DuplicateAlias";
            }
        }

        return "OK";
    }

    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("عذراً، لا يمكن العثور على المنتج رقم " + id);
        }
        repo.deleteById(id);
    }
}
