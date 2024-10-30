package com.company.orderservice.external.response;

import com.company.orderservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private PaymentMode paymentMode;
    private Long amount;
    private Instant paymentDate;
    private Long orderId;
}
