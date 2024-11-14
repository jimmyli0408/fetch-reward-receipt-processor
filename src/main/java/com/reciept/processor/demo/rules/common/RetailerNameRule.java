package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RetailerNameRule implements PointRule {

    @Value("${points.rules.total.retailName.reward}")
    private int retailNameRewardPoints;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {
        String retailerName = receipt.getRetailer();
        int point = 0;
        for (Character c : retailerName.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                point += retailNameRewardPoints;
            }
        }
        return point;
    }
}
