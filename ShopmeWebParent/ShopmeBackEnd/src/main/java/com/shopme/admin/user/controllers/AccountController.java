package com.shopme.admin.user.controllers;

import com.shopme.admin.security.ShompeUserDetails;
import com.shopme.admin.user.services.UserService;
import com.shopme.admin.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import com.shopme.common.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    private UserService service;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShompeUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = service.getUserByEmail(email);
        model.addAttribute("user", user);
        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String updateDetails(@AuthenticationPrincipal ShompeUserDetails loggedUser, User user, RedirectAttributes thRa, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            fileName = FileUploadUtil.renameFile(fileName);
            user.setImage(fileName);
            User savedUser = service.updateAccount(user);
            String uploadDir = "user-image/" + savedUser.getId();

            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getImage().isEmpty()) user.setImage(null);
            service.updateAccount(user);
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        thRa.addFlashAttribute("message", "Your account details have been updated successfully.");

        return "redirect:/account";
    }

}
