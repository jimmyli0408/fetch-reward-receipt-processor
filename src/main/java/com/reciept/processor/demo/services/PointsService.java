package com.reciept.processor.demo.services;

import com.reciept.processor.demo.models.ReceiptRequest;

public interface PointsService {

    public int calculatePoints(ReceiptRequest receipt, String id);
}
