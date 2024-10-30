package com.company.orderservice.service;

import com.company.orderservice.model.OrderRequest;
import com.company.orderservice.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
