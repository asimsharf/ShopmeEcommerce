package com.shopme.admin.user.apis;

import com.shopme.admin.response.TheResponse;
import com.shopme.admin.user.services.UserService;
import com.shopme.common.entity.User;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        return service.isEmailUnique(id, email) == null ? "OK" : "Duplicated";
    }

    @GetMapping("/users/list_users")
    public ResponseEntity<Object> listFirstPage() {
        return listByPage(1);
    }

    @GetMapping("/users/list_users/page/{pageNum}")
    public ResponseEntity<Object> listByPage(@PathVariable(name = "pageNum") int pageNum) {
        Page<User> page = service.listByPage(pageNum, "firstName", "asc", null);
        List<User> listUsers = page.getContent();

        long startCount = (long) (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endCount = startCount + UserService.USER_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        return TheResponse.getResponse("list_users", HttpStatus.OK, listUsers, 1, pageNum, page.getTotalPages(), startCount, endCount, "/ShopmeAdmin/api/users/list_users");
    }

}
