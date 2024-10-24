package com.company.PaymentService.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long orderId;

    private String paymentMode;

    private Instant paymentDate;

    private String paymentStatus;

    private Long amount;
}
