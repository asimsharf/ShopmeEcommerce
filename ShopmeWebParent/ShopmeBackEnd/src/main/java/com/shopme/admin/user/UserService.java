package com.shopme.admin.user;


import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository theRepo) {
        this.userRepo = theRepo;
    }

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public void save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
         encodePassword(user);
        userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User get(Integer id) {
        return userRepo.findById(id).get();
    }

    public void delete(Integer id) {
        userRepo.deleteById(id);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

}
