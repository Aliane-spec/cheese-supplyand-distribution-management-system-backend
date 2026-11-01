package com.example.cheesesupply.controller;
import com.example.cheesesupply.domain.DeliveryBatch;
import com.example.cheesesupply.service.DeliveryBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/delivery-batches")
public class DeliveryBatchController {
    @Autowired
    private DeliveryBatchService deliveryBatchServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDeliveryBatch(
            @RequestParam UUID supplyOrderId,
            @RequestBody DeliveryBatch deliveryBatch) {
        String returnedMessage = deliveryBatchServe.saveDeliveryBatch(supplyOrderId, deliveryBatch);
        if (returnedMessage.equals("The supply order with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else if (returnedMessage.equals("Delivery batches are only for DELIVERED supply orders")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }
    @GetMapping(value = "/retailer/{licenseNumber}")
    public ResponseEntity<?> getDeliveryBatchesByRetailer(@PathVariable String licenseNumber) {
        return new ResponseEntity<>(
                deliveryBatchServe.getDeliveryBatchesByRetailer(licenseNumber), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDeliveryBatch(@PathVariable UUID id) {
        String returnedMessage = deliveryBatchServe.deleteDeliveryBatch(id);
        if (returnedMessage.equals("The delivery batch with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
