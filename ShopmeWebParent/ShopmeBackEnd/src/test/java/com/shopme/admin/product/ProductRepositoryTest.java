package com.shopme.admin.product;

import com.shopme.admin.product.repositories.ProductRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateProduct() {

        Brand brand = testEntityManager.find(Brand.class, 10);
        Category category = testEntityManager.find(Category.class, 15);

        Product product = new Product();
        product.setName("Samsung Galaxy S21 Ultra");
        product.setAlias("samsung_galaxy_s21_ultra");
        product.setShortDescription("New Samsung Galaxy S21 Ultra");
        product.setFullDescription("This is a description of Samsung Galaxy S21 Ultra");
        product.setPrice(1200);
        product.setCost(1000);
        product.setEnabled(true);
        product.setInStock(true);

        product.setBrand(brand);
        product.setCategory(category);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assert (savedProduct.getId() > 0);

    }


    @Test
    public void testListAllProducts() {
        Iterable<Product> products = repo.findAll();
        products.forEach(System.out::println);
    }

    @Test
    public void testGetProduct() {
        Integer id = 1;
        if (repo.findById(id).isPresent()) {
            Product product = repo.findById(id).get();
            System.out.println(product.getName());
            assertThat(product).isNotNull();
        }
    }

    @Test
    public void testUpdateProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(1000);
        repo.save(product);
        Product updatedProduct = testEntityManager.find(Product.class, id);
        assertThat(updatedProduct.getPrice()).isEqualTo(1000);
    }

    @Test
    public void testSaveProductWithImages() {
        Integer productId = 1;
        Product product = repo.findById(productId).get();
        product.addExtraImage("image_1.jpg");
        product.addExtraImage("image_2.jpg");
        product.addExtraImage("image_3.jpg");
        Product savedProduct = repo.save(product);
        assertThat(savedProduct.getImages().size()).isEqualTo(3);
    }

    @Test
    public void testSaveProductWithDetails() {
        Integer productId = 55;
        Product product = repo.findById(productId).get();

        product.addDetail("Display", "15.6 inch");
        product.addDetail("RAM", "8 GB");
        product.addDetail("Storage", "1 TB SSD");
        product.addDetail("Processor", "Core i7 10th Gen");

        Product savedProduct = repo.save(product);
        assertThat(savedProduct.getDetails().size()).isEqualTo(4);

    }

}
