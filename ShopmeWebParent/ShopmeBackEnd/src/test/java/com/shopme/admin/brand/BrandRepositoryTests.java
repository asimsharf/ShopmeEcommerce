//package com.shopme.admin.brand;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.shopme.admin.brand.repositories.BrandRepository;
//import com.shopme.common.entity.Brand;
//import com.shopme.common.entity.Category;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class BrandRepositoryTests {
//
//    @Autowired
//    private BrandRepository repo;
//
//    @Test
//    public void testCreateBrand() {
//        Category laptops = new Category(6);
//        Brand acer = new Brand("Acer");
//        acer.getCategories().add(laptops);
//
////        Brand savedBrand = repo.save(acer);
//
////        assertThat(savedBrand).isNotNull();
////        assertThat(savedBrand.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testFindAll() {
//        Iterable<Brand> brands = repo.findAll();
//        brands.forEach(System.out::println);
//
//        assertThat(brands).isNotEmpty();
//    }
//
//
//}
