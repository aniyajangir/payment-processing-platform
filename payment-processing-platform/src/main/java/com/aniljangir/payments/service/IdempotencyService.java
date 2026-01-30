package com.aniljangir.payments.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IdempotencyService {

    private final Set<String> processedKeys = ConcurrentHashMap.newKeySet();

    public boolean isDuplicate(String transactionId, String externalReference) {
        String key = transactionId + "|" + externalReference;
        return !processedKeys.add(key);
    }
}
