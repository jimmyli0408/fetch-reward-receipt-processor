package com.reciept.processor.demo.controller;

import com.reciept.processor.demo.error.RecordNotFoundException;
import com.reciept.processor.demo.models.PointsResponse;
import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.models.ReceiptResponse;
import com.reciept.processor.demo.services.PointsService;
import com.reciept.processor.demo.services.ReceiptStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    final
    ReceiptStore receiptStoreService;

    final
    PointsService pointsService;

    public ReceiptController(ReceiptStore receiptStoreService, PointsService pointsService) {
        this.receiptStoreService = receiptStoreService;
        this.pointsService = pointsService;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipt(@RequestBody ReceiptRequest receiptRequest) {

        if (receiptRequest == null) {
            logger.debug("Received an empty request");
            throw new IllegalArgumentException("Request body cannot be empty");
        }
        String receiptId = receiptStoreService.saveReceipt(receiptRequest);

        return ResponseEntity.ok(new ReceiptResponse(receiptId));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        ReceiptRequest receipt = receiptStoreService.getReceipt(id);

        if (receipt == null) {
            logger.error("Unable to find a receipt record by receipt ID: {}", id);
            throw new RecordNotFoundException("Unable to find a receipt record by receipt ID");
        }

        int rewardPoints = pointsService.calculatePoints(receipt, id);

        return ResponseEntity.ok(new PointsResponse(rewardPoints));
    }

}
