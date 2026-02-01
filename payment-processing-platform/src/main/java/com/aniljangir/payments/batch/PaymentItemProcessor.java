package com.aniljangir.payments.batch;

import com.aniljangir.payments.domain.PaymentStatus;
import com.aniljangir.payments.domain.PaymentTransaction;
import com.aniljangir.payments.service.IdempotencyService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentItemProcessor implements ItemProcessor<PaymentTransaction, PaymentTransaction> {

    private final IdempotencyService idempotencyService;

    public PaymentItemProcessor(IdempotencyService idempotencyService) {
        this.idempotencyService = idempotencyService;
    }

    @Override
    public PaymentTransaction process(PaymentTransaction item) {

        boolean duplicate = idempotencyService
                .isDuplicate(item.getTransactionId(), item.getExternalReference());

        if (duplicate) {
            item.setStatus(PaymentStatus.DUPLICATE);
            item.setFailureReason("DUPLICATE_TRANSACTION");
            return item;
        }

        if (item.getAmount() == null || item.getAmount().signum() <= 0) {
            item.setStatus(PaymentStatus.FAILED);
            item.setFailureReason("INVALID_AMOUNT");
            return item;
        }

        if (item.getCurrency() == null || item.getCurrency().isBlank()) {
            item.setStatus(PaymentStatus.FAILED);
            item.setFailureReason("INVALID_CURRENCY");
            return item;
        }

        if (item.getCreatedAt() == null) {
            item.setCreatedAt(LocalDateTime.now());
        }

        item.setStatus(PaymentStatus.SUCCESS);
        item.setFailureReason(null);
        return item;
    }




}