package com.company.OrderService.service;

import com.company.OrderService.config.exception.CustomException;
import com.company.OrderService.external.client.PaymentService;
import com.company.OrderService.external.client.ProductService;
import com.company.OrderService.model.dto.OrderDTO;
import com.company.OrderService.model.dto.PaymentDTO;
import com.company.OrderService.model.entity.Order;
import com.company.OrderService.model.form.OrderCreateForm;
import com.company.OrderService.model.form.PaymentForm;
import com.company.OrderService.repository.OrderRepository;
import com.company.ProductService.model.dto.ProductDTO;
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
        order = orderRepository.save(order);

        log.info("Call payment service to complete the payment.");

        PaymentForm paymentForm = PaymentForm.builder()
                .paymentMode(orderCreateForm.getPaymentMode())
                .orderId(order.getOrderId())
                .amount(orderCreateForm.getTotalAmount())
                .referenceNumber("123")
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

    @Override
    public OrderDTO getOrderById(Long id) {

        log.info("Getting order by id {}", id);

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new CustomException("Not found order", "NOT_FOUND", 404)
        );

        log.info("Product service to fetch the product id: {}", id);

        ProductDTO productDTO = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/api/v1/products/" + order.getProductId(),
                ProductDTO.class
        );

        log.info(("Fetch the product success."));

        OrderDTO.ProductDetail productDetail = OrderDTO.ProductDetail.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .productId(productDTO.getProductId())
                .quantity(productDTO.getQuantity())
                .build();

        log.info("Payment service to fetch the order id: {}", order.getOrderId());

        PaymentDTO paymentDTO = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/api/v1/payments/orders/" + order.getOrderId(),
                PaymentDTO.class
        );
        log.info(("Fetch the payment success."));

        log.info("Get order by id {} success", id);

        return OrderDTO.builder()
                .orderDate(order.getOrderData())
                .orderId(order.getOrderId())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .productDetail(productDetail)
                .paymentDetail(paymentDTO)
                .build();
    }
}
