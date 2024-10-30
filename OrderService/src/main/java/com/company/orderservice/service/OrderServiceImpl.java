package com.company.orderservice.service;

import com.company.orderservice.entity.Order;
import com.company.orderservice.external.client.ProductService;
import com.company.orderservice.model.OrderRequest;
import com.company.orderservice.model.PaymentMode;
import com.company.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order ...{}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating order with status CREATED.");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .productId(orderRequest.getProductId())
                .orderStatus("CREATED")
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .build();

        order = orderRepository.save(order);

        log.info("Create order success!!!");
        return order.getId();
    }
}
