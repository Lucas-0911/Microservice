package com.compant.paymentservice.repository;

import com.compant.paymentservice.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    TransactionDetails findByOrderId(Long orderId);
}
