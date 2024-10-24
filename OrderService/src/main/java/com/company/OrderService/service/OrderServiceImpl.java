package com.company.OrderService.service;

import com.company.OrderService.external.client.PaymentService;
import com.company.OrderService.external.client.ProductService;
import com.company.OrderService.model.entity.Order;
import com.company.OrderService.model.form.OrderCreateForm;
import com.company.OrderService.model.form.PaymentForm;
import com.company.OrderService.repository.OrderRepository;
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

    @Autowired
    private PaymentService paymentService;

    @Override
    public Long placeOrder(OrderCreateForm orderCreateForm) {
        //Order entity --> Save data with status order created
        //Product service ->  Reduce quantity
        //Payment Service -> Payment -> Success -> COMPLETE, else CANCELLED

        log.info("Placing order request {}", orderCreateForm);

        productService.reduceQuantity(orderCreateForm.getProductId(), orderCreateForm.getQuantity());

        log.info("Creating order with status CREATED");

        Order order = Order.builder()
                .amount(orderCreateForm.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderCreateForm.getProductId())
                .orderData(Instant.now())
                .quantity(orderCreateForm.getQuantity())
                .build();

        log.info("Call payment service to complete the payment.");

        PaymentForm paymentForm = PaymentForm.builder()
                .paymentMode(orderCreateForm.getPaymentMode())
                .orderId(order.getOrderId())
                .amount(orderCreateForm.getTotalAmount())
                .build();
        String orderStatus = null;
        
        try {
            paymentService.doPayment(paymentForm);
            log.info(("Payment success!!!"));
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.info("Error in payment. {}", e.toString());
            orderStatus = "PAYMENT_FAIL";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order places successfully with Order Id: {}", order.getOrderId());
        return order.getOrderId();
    }
}
