package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeRule implements PointRule {

    @Value("${points.rules.time.start}")
    private String startTime;
    @Value("${points.rules.time.end}")
    private String endTime;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        LocalTime purchaseTime = receipt.getPurchaseTime();
        return purchaseTime.isAfter(start) && purchaseTime.isBefore(end) ? 10 : 0;
    }
}
