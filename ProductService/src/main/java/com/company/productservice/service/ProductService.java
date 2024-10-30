package com.company.productservice.service;

import com.company.productservice.model.ProductRequest;
import com.company.productservice.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse fetchProductById(Long id);

    void reduceQuantity(Long id, Long quantity);
}
