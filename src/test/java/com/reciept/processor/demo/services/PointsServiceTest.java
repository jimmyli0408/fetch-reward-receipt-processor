package com.reciept.processor.demo.services;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import com.reciept.processor.demo.rules.common.DateRule;
import com.reciept.processor.demo.rules.common.ItemRule;
import com.reciept.processor.demo.services.impl.PointsServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointsServiceTest {

    @Test
    public void testCalculatePoints() {

        ReceiptRequest receipt = new ReceiptRequest();
        PointRule rule1 = mock(DateRule.class);
        PointRule rule2 = mock(ItemRule.class);

        when(rule1.calculatePointsBasedOnRule(receipt)).thenReturn(10);
        when(rule2.calculatePointsBasedOnRule(receipt)).thenReturn(20);

        PointsService pointsService = new PointsServiceImpl(Arrays.asList(rule1, rule2));

        int points = pointsService.calculatePoints(receipt, "12345");

        assertEquals(30, points);

    }
}
