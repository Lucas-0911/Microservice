package com.company.PaymentService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "transaction_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "amount")
    private Long amount;
}
