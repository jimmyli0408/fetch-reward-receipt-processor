package com.reciept.processor.demo.controller;

import com.reciept.processor.demo.error.RecordNotFoundException;
import com.reciept.processor.demo.models.PointsResponse;
import com.reciept.processor.demo.models.ReceiptRequest;
import com.reciept.processor.demo.models.ReceiptResponse;
import com.reciept.processor.demo.services.PointsService;
import com.reciept.processor.demo.services.ReceiptStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptControllerTest {

    @Mock
    private ReceiptStore receiptStore;

    @Mock
    private PointsService pointsService;

    @InjectMocks
    private ReceiptController receiptController;

    @Test
    public void testProcessReceipt_WhenReceiptDoesNotNull() {

        ReceiptRequest receipt = new ReceiptRequest();
        receipt.setRetailer("Target");
        String expectedId = "example-id";

        when(receiptStore.saveReceipt(receipt)).thenReturn(expectedId);

        ResponseEntity<ReceiptResponse> response = receiptController.processReceipt(receipt);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedId, response.getBody().getId());
        verify(receiptStore, times(1)).saveReceipt(receipt);
    }

    @Test
    public void testProcessReceipt_WhenReceiptNull() {
        try {
            receiptController.processReceipt(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Request body cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testGetPoints_WhenReceiptExists() {

        String receiptId = "12345";
        ReceiptRequest receipt = new ReceiptRequest();
        receipt.setRetailer("Target");
        when(receiptStore.getReceipt(receiptId)).thenReturn(receipt);
        when(pointsService.calculatePoints(receipt, receiptId)).thenReturn(6);

        ResponseEntity<PointsResponse> response = receiptController.getPoints(receiptId);
        PointsResponse expectedPointsResponse = new PointsResponse(6);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedPointsResponse, response.getBody());
        verify(receiptStore, times(1)).getReceipt(receiptId);
        verify(pointsService, times(1)).calculatePoints(receipt, receiptId);
    }

    @Test
    public void testGetPoints_WhenReceiptDoestNotExist() {

        String receiptId = "12345";
        when(receiptStore.getReceipt(receiptId)).thenReturn(null);

        assertThrows(RecordNotFoundException.class, () -> {
            receiptController.getPoints(receiptId);
        });
        verify(receiptStore, times(1)).getReceipt(receiptId);
        verify(pointsService, never()).calculatePoints(any(), any());
    }
}
