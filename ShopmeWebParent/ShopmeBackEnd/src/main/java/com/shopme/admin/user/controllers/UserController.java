package com.shopme.admin.user.controllers;

import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.exceptions.UserNotFoundException;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.admin.user.services.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService theService) {
        this.service = theService;
    }

    @GetMapping("/users")
    public String listFirstPage(Model model) {
        return listByPage(1, "id", "desc", null, model);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, Model model) {
        Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<User> listUsers = page.getContent();

        long startCount = (long) (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endCount = startCount + UserService.USER_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "users/index";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("pageTitle", "Create New User");
        User user = new User();
        model.addAttribute("user", user);

        user.setEnabled(true);
        List<Role> listRoles = service.listRoles();
        model.addAttribute("listRoles", listRoles);

        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes thRa, @RequestParam("fileImage") MultipartFile multipartFile)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            fileName = FileUploadUtil.renameFile(fileName);
            user.setPhotos(fileName);
            User savedUser = service.save(user);
            String uploadDir = "user-photos/" + savedUser.getId();

            if (FileUploadUtil.isDirExists(uploadDir)) FileUploadUtil.cleanDir(uploadDir);

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        if (user.getPhotos().isEmpty()) user.setPhotos(null);
        thRa.addFlashAttribute("message", "The Category has been saved successfully.");
        service.save(user);


        return getRedirectURLtoAffectedUser(user);
    }

    private String getRedirectURLtoAffectedUser(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes thRa) {
        try {
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            User user = service.get(id);
            model.addAttribute("user", user);
            List<Role> listRoles = service.listRoles();
            model.addAttribute("listRoles", listRoles);
            return "users/user_form";
        } catch (UserNotFoundException ex) {
            thRa.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes thRa) throws IOException {
        try {
            service.delete(id);
            FileUploadUtil.removeDir("user-photos/" + id);

            thRa.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully.");
        } catch (UserNotFoundException ex) {
            thRa.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes ra) {
        service.updateEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;
        ra.addFlashAttribute("message", message);
        return "redirect:/users";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.listAll();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.listAll();
        UserPdfExporter exporter = new UserPdfExporter();
        exporter.export(listUsers, response);
    }

}
