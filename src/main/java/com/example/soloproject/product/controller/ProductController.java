package com.example.soloproject.product.controller;

import com.example.soloproject.admin.controller.AdminController;
import com.example.soloproject.product.dto.ProductCreateRequest;
import com.example.soloproject.product.dto.ProductResponse;
import com.example.soloproject.product.dto.ProductUpdateRequest;
import com.example.soloproject.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductCreateRequest request, HttpSession session) {
        Long loginAdminId = getLoginAdminId(session);

        return productService.create(request, loginAdminId);
    }

    //단건
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    //목록 조회
    @GetMapping
    public Page<ProductResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.findAll(page, size);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductUpdateRequest request, HttpSession session) {
        getLoginAdminId(session);

        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, HttpSession session) {
        getLoginAdminId(session);

        productService.delete(id);
    }

    private Long getLoginAdminId(HttpSession session) {
        Object loginAdminId = session.getAttribute(AdminController.LOGIN_ADMIN_ID);

        if (loginAdminId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return (Long) loginAdminId;
    }
}
