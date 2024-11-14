package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateRule implements PointRule {

    @Value("${points.rules.date.odd.reward}")
    private int dateOddReward;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {
        int day =  receipt.getPurchaseDate().getDayOfMonth();
        return day % 2 == 0 ? 0 : dateOddReward;
    }
}
