package com.shopme.admin.product.repositories;

import com.shopme.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);

    Product findByAlias(String alias);

    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);


}
