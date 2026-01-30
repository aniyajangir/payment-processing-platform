package com.aniljangir.payments.batch;

import com.aniljangir.payments.domain.PaymentStatus;
import com.aniljangir.payments.domain.PaymentTransaction;
import com.aniljangir.payments.service.IdempotencyService;
import org.springframework.batch.item.ItemProcessor;

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
            return item;
        }

        // simulate validation
        if (item.getAmount().signum() <= 0) {
            item.setStatus(PaymentStatus.FAILED);
            throw new IllegalArgumentException("Invalid amount");
        }

        item.setStatus(PaymentStatus.SUCCESS);
        return item;
    }
}