package com.compant.paymentservice.controller;

import com.compant.paymentservice.model.PaymentRequest;
import com.compant.paymentservice.model.PaymentResponse;
import com.compant.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody
                                          PaymentRequest paymentRequest) {
        Long paymentId = paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrder(@PathVariable Long orderId) {

        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrder(orderId);


        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
