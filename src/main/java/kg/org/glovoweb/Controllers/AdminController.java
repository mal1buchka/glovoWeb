package kg.org.glovoweb.Controllers;


import kg.org.glovoweb.DTOs.userWithRoleDTO;
import kg.org.glovoweb.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/with-roles")
    public List<userWithRoleDTO> getAllUsersWithRoles() {
        return userService.getAllUsersWithRoles();
    }


}
