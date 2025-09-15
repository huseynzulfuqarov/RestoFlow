package com.restoflow.repository;

import com.restoflow.domain.product.Product;

import java.util.List;
import java.util.Optional;

public class ProductRepository implements Repository<Product, Long> {
    @Override
    public Product save(Product entity) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
