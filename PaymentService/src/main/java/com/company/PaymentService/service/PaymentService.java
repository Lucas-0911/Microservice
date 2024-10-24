package com.company.PaymentService.service;

import com.company.PaymentService.model.PaymentRequest;
import com.company.PaymentService.model.dto.PaymentDTO;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentDTO getPaymentByOrderId(Long orderId);
}
