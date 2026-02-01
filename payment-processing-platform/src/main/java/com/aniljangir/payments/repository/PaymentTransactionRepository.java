package com.aniljangir.payments.repository;

import com.aniljangir.payments.domain.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByTransactionId(String transactionId);
}
