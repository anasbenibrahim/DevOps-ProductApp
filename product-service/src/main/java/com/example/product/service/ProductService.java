package com.example.product.service;

import com.example.product.dto.*;
import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) { this.repository = repository; }
    public List<ProductResponse> findAll() { return repository.findAll().stream().map(ProductResponse::from).toList(); }
    public ProductResponse findById(Long id) { return ProductResponse.from(get(id)); }
    public ProductResponse create(ProductRequest request) {
        return ProductResponse.from(repository.save(new Product(request.name(), request.description(), request.price(), request.quantity())));
    }
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = get(id);
        product.setName(request.name()); product.setDescription(request.description());
        product.setPrice(request.price()); product.setQuantity(request.quantity());
        return ProductResponse.from(repository.save(product));
    }
    public void delete(Long id) { repository.delete(get(id)); }
    private Product get(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " was not found"));
    }
}
