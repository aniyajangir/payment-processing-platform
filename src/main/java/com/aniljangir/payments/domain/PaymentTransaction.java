package com.aniljangir.payments.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentTransaction {
    private String transactionId;
    private String externalReference;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public PaymentTransaction() {}

    public PaymentTransaction(String transactionId,
                              String externalReference,
                              BigDecimal amount,
                              String currency) {
        this.transactionId = transactionId;
        this.externalReference = externalReference;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }
}
