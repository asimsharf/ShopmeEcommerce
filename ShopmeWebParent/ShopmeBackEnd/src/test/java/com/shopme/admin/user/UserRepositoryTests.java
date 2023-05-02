package com.shopme.admin.user;


import com.shopme.admin.user.repositories.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final Integer theRemovedID = 6;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("12345678");
        User user = new User("admin@admin.com", password, "Admin", "Admin");
        user.setEnabled(true);
        user.addRoles(roleAdmin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

//    @Test
//    public void testCreateNewUserWithTwoRoles() {
//        User user = new User("ahmed121@gmail.com", "12345678", "Ahmed", "Ali");
//
//        Role roleEditor = entityManager.find(Role.class, 3);
//        Role roleAssistant = entityManager.find(Role.class, 4);
//
//        user.addRoles(roleEditor);
//        user.addRoles(roleAssistant);
//
//        User savedUser = userRepository.save(user);
//        assertThat(savedUser.getId()).isGreaterThan(0);
//
//    }
//
//    @Test
//    public void testListAllUsers() {
//        Iterable<User> listUsers = userRepository.findAll();
//        listUsers.forEach(System.out::println);
//        assertThat(listUsers).size().isGreaterThan(0);
//    }
//
//    @Test
//    public void testGetUserById() {
//        Integer theRemovedID = 1;
//        User user = userRepository.findById(theRemovedID).get();
//        System.out.println(user);
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    public void testUpdateUserDetails() {
//        Integer theRemovedID = 1;
//        User user = userRepository.findById(theRemovedID).get();
//        user.setEnabled(true);
//        user.setEmail("m@m.com");
//        userRepository.save(user);
//
//    }
//
//    @Test
//    public void testUpdateUserRoles() {
//        User user = userRepository.findById(theRemovedID).get();
//        Role roleEditor = entityManager.find(Role.class, 3);
//        Role roleAssistant = entityManager.find(Role.class, 4);
//        user.getRoles().remove(roleAssistant);
//        user.addRoles(roleEditor);
//        userRepository.save(user);
//    }
//
//    @Test
//    public void testDeleteUser() {
//        User user = userRepository.findById(theRemovedID).get();
//        user.getRoles().clear();
//        userRepository.save(user);
//        userRepository.deleteById(theRemovedID);
//    }
//
//    @Test
//    public void testGetUserByEmail() {
//        String email = "asimsharf@gmail.com";
//        User user = userRepository.getUserByEmail(email);
//        assertThat(user).isNotNull();
//    }
//
//    @Test
//    public void testCountById() {
//        Integer id = 30;
//        Long countById = userRepository.countById(id);
//        assertThat(countById).isNotNull().isGreaterThan(0);
//    }
//
//    @Test
//    public void testDisableUser() {
//        Integer id = 3;
//        userRepository.updateEnabledStatus(id, false);
//    }
//
//    @Test
//    public void testEnableUser() {
//        Integer id = 3;
//        userRepository.updateEnabledStatus(id, true);
//    }
//
//
//    @Test
//    public void testListFirstPage() {
//        int pageNumber = 0;
//        int pageSize = 5;
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = userRepository.findAll(pageable);
//        List<User> listUsers = page.getContent();
//        listUsers.forEach(System.out::println);
//
//        assertThat(listUsers.size()).isEqualTo(pageSize);
//    }
//
//    @Test
//    public void testSearchUsers() {
//        String keyword = "asim";
//        int pageNumber = 0;
//        int pageSize = 5;
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = userRepository.findAll(keyword,pageable);
//        List<User> listUsers = page.getContent();
//        listUsers.forEach(System.out::println);
//
//        assertThat(listUsers.size()).isGreaterThan(0);
//    }

}
