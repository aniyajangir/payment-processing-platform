package com.aniljangir.payments.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payment_transaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "transaction_id")
        }
)
@Getter
@Setter
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;
    @Column(name = "external_reference", nullable = false)
    private String externalReference;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "failure_reason")
    private String failureReason;

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
