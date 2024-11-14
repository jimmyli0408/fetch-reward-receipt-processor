package com.reciept.processor.demo.rules.common;

import com.reciept.processor.demo.models.Item;
import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ItemRule implements PointRule {

    @Value("${points.rules.item.multiple.price.factor}")
    private String multiplePriceFactor;
    @Value("${points.rules.item.description.divide.factor}")
    private int descriptionDivideFactor;

    @Override
    public int calculatePointsBasedOnRule(ReceiptRequest receipt) {
        int point = 0;
        List<Item> items = receipt.getItems();
        point += (items.size()/2) * 5;

        for (Item item : items) {
            String description = item.getShortDescription().trim();
            if (description.length() % descriptionDivideFactor == 0) {
                BigDecimal price = item.getPrice();
                point+= price.multiply(new BigDecimal(multiplePriceFactor)).setScale(0, BigDecimal.ROUND_UP).intValue();
            }
        }

        return point;
    }
}
