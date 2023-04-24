package com.shopme.admin.category.repositories;
import com.shopme.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    public List<Category> findRootCategories();


    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    public Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.alias = ?1")
    public Category findByAlias(String alias);


    @Query("SELECT c FROM Category c WHERE CONCAT(c.id, ' ', c.name, ' ', c.alias) LIKE %?1%")
    public Page<Category> findAll(String keyword, Pageable pageable);

    @Query("UPDATE Category u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    public Long countById(Integer id);

}
