package com.aniljangir.payments.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {

    private final Set<String> processedKeys = ConcurrentHashMap.newKeySet();

    public boolean isDuplicate(String transactionId, String externalReference) {
        String key = transactionId + "|" + externalReference;
        return !processedKeys.add(key);
    }
}
