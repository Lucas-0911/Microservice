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
public class OrderCreateForm {

    private Long productId;
    private Long totalAmount;
    private Long quantity;
    private PaymentMode paymentMode;

}
