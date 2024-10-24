package com.company.PaymentService.controller;

import com.company.PaymentService.model.PaymentRequest;
import com.company.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping
    private ResponseEntity<Long>  doPayment(@RequestBody
                                            PaymentRequest paymentRequest){

        Long paymentId = paymentService.doPayment(paymentRequest);
        return  new ResponseEntity<>(paymentId, HttpStatus.OK);
    }
}
