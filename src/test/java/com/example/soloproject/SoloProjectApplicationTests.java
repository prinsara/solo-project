package com.example.soloproject;

import com.example.soloproject.admin.entity.Admin;
import com.example.soloproject.admin.repository.AdminRepository;
import com.example.soloproject.product.dto.ProductCreateRequest;
import com.example.soloproject.product.dto.ProductResponse;
import com.example.soloproject.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SoloProjectApplicationTests {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
    }

    @Test
    void createProductWithAdmin() {
        Admin admin = adminRepository.save(new Admin("gygim"));

        ProductResponse created = productService.create(new ProductCreateRequest("모니터", 50, admin.getId()));

        assertThat(created.name()).isEqualTo("모니터");
        assertThat(created.price()).isEqualTo(50);
        assertThat(created.adminName()).isEqualTo("gygim");
    }
}
