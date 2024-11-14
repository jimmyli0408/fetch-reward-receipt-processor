package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MultipleAmountRule implements PointRule {

    @Value("${points.rules.total.multiple.factor}")
    private String multipleFactor;
    @Value("${points.rules.total.multiple.reward}")
    private int multipleAmountRewardPoints;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {
        BigDecimal totalAmount = receipt.getTotal();
        return totalAmount.remainder(new BigDecimal(multipleFactor)).compareTo(BigDecimal.ZERO) == 0 ? multipleAmountRewardPoints : 0;
    }
}
