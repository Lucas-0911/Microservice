package com.company.OrderService.model.form;

import com.company.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentForm {
    private Long orderId;
    private Long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
