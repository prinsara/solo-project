package com.example.soloproject.product.service;

import com.example.soloproject.admin.entity.Admin;
import com.example.soloproject.admin.repository.AdminRepository;
import com.example.soloproject.product.dto.ProductCreateRequest;
import com.example.soloproject.product.dto.ProductResponse;
import com.example.soloproject.product.dto.ProductUpdateRequest;
import com.example.soloproject.product.entity.Product;
import com.example.soloproject.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    public ProductService(ProductRepository productRepository, AdminRepository adminRepository) {
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Admin admin = adminRepository.findById(request.adminId())
                .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));

        Product product = new Product(request.name(), request.price());
        product.assignAdmin(admin);
        Product savedProduct = productRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                getAdminName(savedProduct)
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                getAdminName(product)
        );
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        getAdminName(product)
                ));
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        product.update(request.name(), request.price());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                getAdminName(product)
        );
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        productRepository.delete(product);
    }

    private String getAdminName(Product product) {
        if (product.getAdmin() == null) {
            return null;
        }

        return product.getAdmin().getName();
    }
}
