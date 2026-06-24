package com.example.soloproject.admin.controller;

import com.example.soloproject.admin.dto.AdminResponse;
import com.example.soloproject.admin.dto.AdminSignupRequest;
import com.example.soloproject.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse signup(@RequestBody AdminSignupRequest request) {
        return adminService.signup(request);
    }
}
