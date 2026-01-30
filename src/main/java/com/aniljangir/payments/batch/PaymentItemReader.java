package com.aniljangir.payments.batch;

import com.aniljangir.payments.domain.PaymentTransaction;
import org.springframework.batch.item.ItemReader;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class PaymentItemReader implements ItemReader<PaymentTransaction> {

    private final Iterator<PaymentTransaction> iterator;

    public PaymentItemReader() {
        List<PaymentTransaction> transactions = List.of(
                new PaymentTransaction("TXN001", "EXT001", BigDecimal.valueOf(100), "INR"),
                new PaymentTransaction("TXN002", "EXT002", BigDecimal.valueOf(200), "INR"),
                new PaymentTransaction("TXN001", "EXT001", BigDecimal.valueOf(100), "INR") // duplicate
        );
        this.iterator = transactions.iterator();
    }

    @Override
    public PaymentTransaction read() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
