package com.company.productservice.service;

import com.company.productservice.entity.Product;
import com.company.productservice.exception.ProductServiceCustomException;
import com.company.productservice.model.ProductRequest;
import com.company.productservice.model.ProductResponse;
import com.company.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding product ....");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        product = productRepository.save(product);

        log.info("Add product success !!!");
        return product.getProductId();
    }

    @Override
    public ProductResponse fetchProductById(Long id) {
        log.info("Find product for productId {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductServiceCustomException("Product with given id not found.", "NOT_FOUND")
        );


        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        log.info("Find product success!!!");
        return productResponse;
    }

    @Override
    public void reduceQuantity(Long id, Long quantity) {
        log.info("Reduce productId: {}, quantity: {}", id, quantity);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductServiceCustomException("Product " + id + " not found.", "NOT_FOUND")
        );

        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product does not have suffcient quantity", "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        log.info("Reduce quantity for productId {} success !!!", id);
    }
}
