package com.company.ProductService.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private Long price;
    private Long quantity;
}
