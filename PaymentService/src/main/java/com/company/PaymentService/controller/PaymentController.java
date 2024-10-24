package com.company.PaymentService.controller;

import com.company.PaymentService.model.PaymentRequest;
import com.company.PaymentService.model.dto.PaymentDTO;
import com.company.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    private ResponseEntity<Long> doPayment(@RequestBody
                                           PaymentRequest paymentRequest) {

        Long paymentId = paymentService.doPayment(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    private ResponseEntity<PaymentDTO> getPaymentByOrderId(@PathVariable
                                                           Long orderId) {
        PaymentDTO paymentDTO = paymentService.getPaymentByOrderId(orderId);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }
}
