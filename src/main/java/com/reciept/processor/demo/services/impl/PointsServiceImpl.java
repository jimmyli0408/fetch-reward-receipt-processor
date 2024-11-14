package com.reciept.processor.demo.services.impl;

import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.rules.PointRule;
import com.reciept.processor.demo.services.PointsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointsServiceImpl implements PointsService {

    private Map<String, Integer> pointsMap = new HashMap<>();
    private final List<PointRule> rules;

    public PointsServiceImpl(List<PointRule> rules) {
        this.rules = rules;
    }

    public int calculatePoints(ReceiptRequest receipt, String id) {

        Integer totalPoints = pointsMap.get(id);
        if (totalPoints !=  null) {
            return totalPoints;
        }

        totalPoints = 0;

        for (PointRule rule : rules) {
            totalPoints += rule.calculatePointsBasedOnRule(receipt);
        }

        pointsMap.put(id, totalPoints);

        return totalPoints;
    }
}
