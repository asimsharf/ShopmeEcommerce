package com.shopme.admin.user.services;


import com.shopme.admin.user.exceptions.UserNotFoundException;
import com.shopme.admin.user.repositories.RoleRepository;
import com.shopme.admin.user.repositories.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    public static final int USER_PER_PAGE = 4;

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository theRepo) {
        this.userRepo = theRepo;
    }

    public Page<User> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);
        return userRepo.findAll(pageable);
    }

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        userRepo.save(user);
        return user;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);
        if (userByEmail == null) return null;

        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            return userByEmail;
        } else {
            if (!Objects.equals(userByEmail.getId(), id)) {
                return userByEmail;
            }
        }

        return null;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        userRepo.deleteById(id);
    }

    public void updateEnabledStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }


}
