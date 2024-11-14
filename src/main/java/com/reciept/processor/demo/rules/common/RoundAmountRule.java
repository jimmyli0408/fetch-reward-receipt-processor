package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RoundAmountRule implements PointRule {

    @Value("${points.rules.total.roundDollar.reward}")
    private int roundAmountRewardPoint;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {
        BigDecimal amount = receipt.getTotal();

        return amount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO)  == 0 ? roundAmountRewardPoint : 0;
    }
}
