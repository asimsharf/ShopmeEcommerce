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
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    Integer theDefaultID = 48;
    String theDefaultPassword = "12345678";

    public String theDefaultEmail() {
        String domain = "@shopme.com";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb + domain;
    }

    @Test
    public void testCreateNewUserWithOneRole() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(theDefaultPassword);
        User user = new User(theDefaultEmail(), password, "Admin", "Admin");

        Role roleAdmin = entityManager.find(Role.class, 1);
        user.setEnabled(true);
        user.addRoles(roleAdmin);

        User savedUser = userRepository.save(user);
        theDefaultID = savedUser.getId();
        assertThat(theDefaultID).isGreaterThan(0);

    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(theDefaultPassword);
        User user = new User(theDefaultEmail(), password, "Ahmed", "Ali");

        Role roleEditor = entityManager.find(Role.class, 3);
        Role roleAssistant = entityManager.find(Role.class, 4);
        user.addRoles(roleEditor);
        user.addRoles(roleAssistant);

        User savedUser = userRepository.save(user);
        theDefaultID = savedUser.getId();
        assertThat(theDefaultID).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers() {

        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(System.out::println);
        assertThat(listUsers).size().isGreaterThan(0);
    }

    @Test
    public void testGetUserById() {

        User user = userRepository.findById(theDefaultID).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    // @Test
    // public void testUpdateUserDetails() {

    // User user = userRepository.findById(theDefaultID).get();
    // user.setEnabled(true);
    // user.setEmail("m@m.com");
    // userRepository.save(user);

    // }

    // @Test
    // public void testUpdateUserRoles() {

    // User user = userRepository.findById(theDefaultID).get();
    // Role roleEditor = entityManager.find(Role.class, 3);
    // Role roleAssistant = entityManager.find(Role.class, 4);
    // user.getRoles().remove(roleAssistant);
    // user.addRoles(roleEditor);
    // userRepository.save(user);
    // }

    // @Test
    // public void testDeleteUser() {

    // User user = userRepository.findById(theDefaultID).get();
    // user.getRoles().clear();
    // userRepository.save(user);
    // userRepository.deleteById(theDefaultID);
    // }

    // @Test
    // public void testGetUserByEmail() {

    // User user = userRepository.getUserByEmail(theDefaultEmail);
    // assertThat(user).isNotNull();
    // }

    // @Test
    // public void testCountById() {

    // Long countById = userRepository.countById(theDefaultID);
    // assertThat(countById).isNotNull().isGreaterThan(0);
    // }

    // @Test
    // public void testDisableUser() {

    // userRepository.updateEnabledStatus(theDefaultID, false);
    // }

    // @Test
    // public void testEnableUser() {

    // userRepository.updateEnabledStatus(theDefaultID, true);
    // }

    // @Test
    // public void testListFirstPage() {
    // int pageNumber = 0;
    // int pageSize = 5;

    // Pageable pageable = PageRequest.of(pageNumber, pageSize);
    // Page<User> page = userRepository.findAll(pageable);
    // List<User> listUsers = page.getContent();
    // listUsers.forEach(System.out::println);

    // assertThat(listUsers.size()).isEqualTo(pageSize);
    // }

    // @Test
    // public void testSearchUsers() {
    // String keyword = "asim";
    // int pageNumber = 0;
    // int pageSize = 5;

    // Pageable pageable = PageRequest.of(pageNumber, pageSize);
    // Page<User> page = userRepository.findAll(keyword, pageable);
    // List<User> listUsers = page.getContent();
    // listUsers.forEach(System.out::println);

    // assertThat(listUsers.size()).isGreaterThan(0);
    // }

}
