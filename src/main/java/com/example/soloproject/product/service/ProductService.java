package com.example.soloproject.product.service;

import com.example.soloproject.product.dto.ProductCreateRequest;
import com.example.soloproject.product.dto.ProductResponse;
import com.example.soloproject.product.dto.ProductUpdateRequest;
import com.example.soloproject.product.entity.Product;
import com.example.soloproject.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product product = new Product(request.name(), request.price());
        Product savedProduct = productRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice()
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        product.update(request.name(), request.price());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        productRepository.delete(product);
    }
}
