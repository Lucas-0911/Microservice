package com.company.ProductService.service;

import com.company.ProductService.model.dto.ProductDTO;
import com.company.ProductService.model.entity.Product;
import com.company.ProductService.model.exception.ProductServiceCustomException;
import com.company.ProductService.model.request.ProductRequest;
import com.company.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product created.");
        return product.getProductId();
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all products ...");
        List<Product> products = productRepository.findAll();
        log.info("Getted all products.");
        return products;
    }

    @Override
    public ProductDTO getProductById(Long id) {

        log.info("Getting product {} ...", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given " + id + " not found.", "PRODUCT_NOT_FOUND"));

        ProductDTO productDTO = new ProductDTO();

        copyProperties(product, productDTO);
        log.info("Getted product.");
        return productDTO;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reduce {} quantity product {}...", quantity, productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException("Product with given " + productId + " not found.", "PRODUCT_NOT_FOUND")
        );

        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product not have sufficient quantity", "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);

        productRepository.save(product);
        log.info(("Product quantity updated successfully"));
    }
}
