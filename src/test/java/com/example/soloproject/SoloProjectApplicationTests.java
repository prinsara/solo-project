package com.example.soloproject;

import com.example.soloproject.product.dto.ProductCreateRequest;
import com.example.soloproject.product.dto.ProductResponse;
import com.example.soloproject.product.dto.ProductUpdateRequest;
import com.example.soloproject.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SoloProjectApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct() {
        ProductResponse created = productService.create(new ProductCreateRequest("모니터", 50));

        assertThat(created.name()).isEqualTo("모니터");
        assertThat(created.price()).isEqualTo(50);
        assertThat(created.id()).isNotNull();
    }

    @Test
    void findProductById() {
        ProductResponse created = productService.create(new ProductCreateRequest("키보드", 30));
        ProductResponse found = productService.findById(created.id());

        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.name()).isEqualTo("키보드");
        assertThat(found.price()).isEqualTo(30);
    }

    @Test
    void updateProduct() {
        ProductResponse created = productService.create(new ProductCreateRequest("마우스", 20));
        ProductResponse updated = productService.update(created.id(), new ProductUpdateRequest("무선 마우스", 25));

        assertThat(updated.id()).isEqualTo(created.id());
        assertThat(updated.name()).isEqualTo("무선 마우스");
        assertThat(updated.price()).isEqualTo(25);
    }
}
