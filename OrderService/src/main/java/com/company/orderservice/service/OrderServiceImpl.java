package com.company.orderservice.service;

import com.company.orderservice.entity.Order;
import com.company.orderservice.exception.CustomException;
import com.company.orderservice.external.client.PaymentService;
import com.company.orderservice.external.client.ProductService;
import com.company.orderservice.external.request.PaymentRequest;
import com.company.orderservice.external.response.PaymentResponse;
import com.company.orderservice.model.OrderRequest;
import com.company.orderservice.model.OrderResponse;
import com.company.orderservice.repository.OrderRepository;
import com.company.productservice.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

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

        log.info("Call to payment ...");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done success");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error payment.");
            orderStatus = "PAYMENT_FAIL";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Create order success!!!");
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        log.info("Get order detail for order id {}", orderId);

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomException("Order not found", "NOT_FOUND", 404)
        );

        log.info("Product service ...");
        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products/" + order.getProductId(),
                ProductResponse.class);

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        log.info("Payment service ....");
        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payments/orders/" + order.getId(), PaymentResponse.class
        );

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .status(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
    }
}
