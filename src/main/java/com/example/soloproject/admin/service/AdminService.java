package com.example.soloproject.admin.service;

import com.example.soloproject.admin.dto.AdminLoginRequest;
import com.example.soloproject.admin.dto.AdminResponse;
import com.example.soloproject.admin.dto.AdminSignupRequest;
import com.example.soloproject.admin.entity.Admin;
import com.example.soloproject.admin.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminResponse signup(AdminSignupRequest request) {
        Admin admin = new Admin(
                request.name(),
                passwordEncoder.encode(request.password())
        );

        Admin savedAdmin = adminRepository.save(admin);

        return new AdminResponse(savedAdmin.getId(), savedAdmin.getName());
    }

    @Transactional(readOnly = true)
    public AdminResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByName(request.name())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return new AdminResponse(admin.getId(), admin.getName());
    }
}
