package com.company.productservice.controller;

import com.company.productservice.model.ProductRequest;
import com.company.productservice.model.ProductResponse;
import com.company.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody
                                           ProductRequest productRequest) {
        Long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> fetchProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.fetchProductById(id);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long id,
                                               @RequestParam Long quantity) {

        productService.reduceQuantity(id, quantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
