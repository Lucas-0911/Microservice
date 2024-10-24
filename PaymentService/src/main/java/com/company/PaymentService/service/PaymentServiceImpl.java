package com.company.PaymentService.service;

import com.company.PaymentService.entity.TransactionDetail;
import com.company.PaymentService.model.PaymentRequest;
import com.company.PaymentService.model.dto.PaymentDTO;
import com.company.PaymentService.repository.TransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .build();

        transactionDetailRepository.save(transactionDetail);

        log.info("Transaction complete with id: {}", transactionDetail.getId());

        return transactionDetail.getId();
    }

    @Override
    public PaymentDTO getPaymentByOrderId(Long orderId) {
        log.info("Get payment for orderid: {}", orderId);

        TransactionDetail transactionDetail = transactionDetailRepository.findByOrderId(orderId);
        if (transactionDetail == null) {
            return null;
        }
        log.info("Get payment success.");
        return PaymentDTO.builder()
                .amount(transactionDetail.getAmount())
                .orderId(transactionDetail.getOrderId())
                .paymentDate(transactionDetail.getPaymentDate())
                .paymentMode(transactionDetail.getPaymentMode())
                .paymentStatus(transactionDetail.getPaymentStatus())
                .build();
    }
}