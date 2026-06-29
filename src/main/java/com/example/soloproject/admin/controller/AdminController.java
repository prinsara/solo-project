package com.example.soloproject.admin.controller;

import com.example.soloproject.admin.dto.AdminLoginRequest;
import com.example.soloproject.admin.dto.AdminResponse;
import com.example.soloproject.admin.dto.AdminSignupRequest;
import com.example.soloproject.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {

    public static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse signup(@RequestBody AdminSignupRequest request) {
        return adminService.signup(request);
    }

    @PostMapping("/login")
    public AdminResponse login(@RequestBody AdminLoginRequest request, HttpSession session) {
        AdminResponse response = adminService.login(request);
        session.setAttribute(LOGIN_ADMIN_ID, response.id());

        return response;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
