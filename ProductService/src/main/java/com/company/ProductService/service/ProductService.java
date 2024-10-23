package com.company.ProductService.service;

import com.company.ProductService.model.dto.ProductDTO;
import com.company.ProductService.model.entity.Product;
import com.company.ProductService.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    List<Product> getAllProducts();

    ProductDTO getProductById(Long id);
}
