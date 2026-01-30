package com.aniljangir.payments.batch;

import com.aniljangir.payments.domain.PaymentTransaction;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class PaymentItemWriter implements ItemWriter<PaymentTransaction> {

    private final List<PaymentTransaction> successStore = new ArrayList<>();
    private final List<PaymentTransaction> failureStore = new ArrayList<>();

    @Override
    public void write(Chunk<? extends PaymentTransaction> items) {

        for (PaymentTransaction txn : items) {
            if (txn.getStatus().name().equals("SUCCESS")) {
                successStore.add(txn);
            } else {
                failureStore.add(txn);
            }
        }

        System.out.println("Written batch -> Success: " +
                successStore.size() + ", Failed/Duplicate: " + failureStore.size());
    }
}