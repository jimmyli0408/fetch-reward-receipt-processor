package com.reciept.processor.demo.rules;

import com.reciept.processor.demo.models.ReceiptRequest;

public interface PointRule {
    int calculatePointsBasedOnRule(ReceiptRequest receipt);
}
