package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService theService) {
        this.service = theService;
    }

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {

        model.addAttribute("pageTitle", "Create New User");

        User user = new User();
        model.addAttribute("user", user);

        user.setEnabled(true);

        List<Role> listRoles = service.listRoles();
        model.addAttribute("listRoles", listRoles);

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes thRa) {
        service.save(user);
        thRa.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes thRa) {
        try {
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");

            User user = service.get(id);
            model.addAttribute("user", user);

            List<Role> listRoles = service.listRoles();
            model.addAttribute("listRoles", listRoles);

            return "user_form";
        } catch (UserNotFoundException ex) {
            thRa.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes thRa) {
        try {
            service.delete(id);
            thRa.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully.");

        } catch (UserNotFoundException ex) {
            thRa.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

}
