package com.compant.paymentservice.service;

import com.compant.paymentservice.model.PaymentRequest;
import com.compant.paymentservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrder(Long orderId);
}
