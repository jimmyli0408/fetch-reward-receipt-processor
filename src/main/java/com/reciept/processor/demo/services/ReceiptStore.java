package com.reciept.processor.demo.services;

import com.reciept.processor.demo.models.ReceiptRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceiptStore {

    private Map<String, ReceiptRequest> receiptMap = new HashMap<>();
    private Map<String, String> receiptUniqueIdMap = new HashMap<>();

    public String saveReceipt(ReceiptRequest receiptRequest) {

        String uniqueReceiptKey = generateUniqueId(receiptRequest);

        if (receiptUniqueIdMap.containsKey(uniqueReceiptKey)) {
            return receiptUniqueIdMap.get(uniqueReceiptKey);
        }
        String receiptId = UUID.randomUUID().toString();
        receiptUniqueIdMap.put(uniqueReceiptKey, receiptId);
        receiptMap.put(receiptId, receiptRequest);
        return receiptId;
    }

    public ReceiptRequest getReceipt(String id) {

        ReceiptRequest receipt = receiptMap.get(id);

        return receipt;
    }

    private String generateUniqueId(ReceiptRequest receipt) {

        try {

            String formattedDate = receipt.getPurchaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            String formattedTime = receipt.getPurchaseTime().format(DateTimeFormatter.ISO_LOCAL_TIME);


            String itemKey = receipt.getItems()
                    .stream()
                    .map(item -> item.getShortDescription() + ":" + item.getPrice().toPlainString())
                    .collect(Collectors.joining(","));

            String rawKey = receipt.getRetailer() + "|"
                    + formattedDate + "|"
                    + formattedTime + "|"
                    + receipt.getTotal().toPlainString() + "|"
                    + itemKey;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawKey.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash for unique receipt ID", e);
        }
    }
}
