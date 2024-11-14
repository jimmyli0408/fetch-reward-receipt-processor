package com.reciept.processor.demo.services;

import com.reciept.processor.demo.models.Item;
import com.reciept.processor.demo.models.ReceiptRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class ReceiptStoreTest {

    private ReceiptStore receiptStore;

    @BeforeEach
    public void setUp() {
        receiptStore = new ReceiptStore();
    }

    @Test
    public void testSaveReceipt_shouldReturnUUId() {

        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setRetailer("Walgreens");
        receiptRequest.setPurchaseDate(LocalDate.of(2022, 1, 2));
        receiptRequest.setPurchaseTime(LocalTime.of(8, 13));
        receiptRequest.setTotal(new BigDecimal("2.65"));
        receiptRequest.setItems(List.of(
                new Item("Pepsi - 12-oz", new BigDecimal("1.25")),
                new Item("Dasani", new BigDecimal("1.40"))
        ));

        String uniqueId = receiptStore.saveReceipt(receiptRequest);

        assertTrue(uniqueId != null && !uniqueId.isEmpty(), "Unique ID should not be null or empty");
        assertEquals(receiptRequest, receiptStore.getReceipt(uniqueId), "Receipt should be saved and retrievable by unique ID");
    }

    @Test
    public void testSaveReceipt_withDuplicateRequest() {
        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setRetailer("Walgreens");
        receiptRequest.setPurchaseDate(LocalDate.of(2022, 1, 2));
        receiptRequest.setPurchaseTime(LocalTime.of(8, 13));
        receiptRequest.setTotal(new BigDecimal("2.65"));
        receiptRequest.setItems(List.of(
                new Item("Pepsi - 12-oz", new BigDecimal("1.25")),
                new Item("Dasani", new BigDecimal("1.40"))
        ));

        // Act: Save the same receipt twice and get the unique IDs
        String uniqueId1 = receiptStore.saveReceipt(receiptRequest);
        String uniqueId2 = receiptStore.saveReceipt(receiptRequest);

        // Assert: Verify that the same unique ID is returned for the duplicate request
        assertEquals(uniqueId1, uniqueId2, "Duplicate receipt should return the same unique ID");
    }




}
