package com.shopme.admin.user;


import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

        @Autowired
        private UserRepository repo;

        @Autowired
        private TestEntityManager entityManager;

        private final Integer theRemovedID = 6;

        @Test
        public void testCreateNewUserWithOneRole() {
                Role roleAdmin = entityManager.find(Role.class, 1);
                User user = new User("mohamed12@gmail.com", "12345678", "Mohamed", "Ali");

                user.addRoles(roleAdmin);

                User savedUser = repo.save(user);
                assertThat(savedUser.getId()).isGreaterThan(0);

        }

        @Test
        public void testCreateNewUserWithTwoRoles() {
                User user = new User("ahmed121@gmail.com", "12345678", "Ahmed", "Ali");

                Role roleEditor =  entityManager.find(Role.class, 3);
                Role roleAssistant =  entityManager.find(Role.class, 4);

                user.addRoles(roleEditor);
                user.addRoles(roleAssistant);

                User savedUser = repo.save(user);
                assertThat(savedUser.getId()).isGreaterThan(0);

        }

        @Test
        public void testListAllUsers() {
                Iterable<User> listUsers = repo.findAll();
                listUsers.forEach(System.out::println);
                assertThat(listUsers).size().isGreaterThan(0);
        }

        @Test
        public void testGetUserById() {
                Integer theRemovedID = 1;
                User user = repo.findById(theRemovedID).get();
                System.out.println(user);
                assertThat(user).isNotNull();
        }

        @Test
        public void testUpdateUserDetails() {
                Integer theRemovedID = 1;
                User user = repo.findById(theRemovedID).get();
                user.setEnabled(true);
                user.setEmail("m@m.com");
                repo.save(user);

        }

        @Test
        public void testUpdateUserRoles() {
                User user = repo.findById(theRemovedID).get();
                Role roleEditor =  entityManager.find(Role.class, 3);
                Role roleAssistant =  entityManager.find(Role.class, 4);

                user.getRoles().remove(roleAssistant);
                user.addRoles(roleEditor);

                repo.save(user);
        }

        @Test
        public void testDeleteUser() {
                User user = repo.findById(theRemovedID).get();
                user.getRoles().clear();
                repo.save(user);
                repo.deleteById(theRemovedID);
        }
}
