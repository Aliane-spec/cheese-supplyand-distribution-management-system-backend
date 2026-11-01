package com.example.cheesesupply.controller;
import com.example.cheesesupply.domain.SupplyOrder;
import com.example.cheesesupply.domain.OrderStatus;
import com.example.cheesesupply.service.SupplyOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/supply-orders")
public class SupplyOrderController {
    @Autowired
    private SupplyOrderService supplyOrderServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveSupplyOrder(@RequestBody SupplyOrder supplyOrder) {
        String returnedMessage = supplyOrderServe.saveSupplyOrder(supplyOrder);
        if (returnedMessage.equals("Supply order saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else if (returnedMessage.equals("Distributor already has an order on that date")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllSupplyOrders() {
        return new ResponseEntity<>(supplyOrderServe.getAllSupplyOrders(), HttpStatus.OK);
    }
    @GetMapping(value = "/by-status")
    public ResponseEntity<?> getSupplyOrdersByStatus(@RequestParam OrderStatus status) {
        return new ResponseEntity<>(supplyOrderServe.getSupplyOrdersByStatus(status), HttpStatus.OK);
    }
    @GetMapping(value = "/between")
    public ResponseEntity<?> getSupplyOrdersBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(supplyOrderServe.getSupplyOrdersBetween(startDate, endDate), HttpStatus.OK);
    }
    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<List<Object[]>> getSupplyOrderStatsByStatus() {
        return new ResponseEntity<>(supplyOrderServe.getSupplyOrderStatsByStatus(), HttpStatus.OK);
    }
    @GetMapping(value = "/page")
    public ResponseEntity<Page<SupplyOrder>> getSupplyOrdersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "orderDate,desc") String sort) {
        String[] sortParts = sort.split(",", 2);
        Sort.Direction direction = sortParts.length == 2
                ? Sort.Direction.fromString(sortParts[1])
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));
        return new ResponseEntity<>(supplyOrderServe.getSupplyOrdersPage(pageable), HttpStatus.OK);
    }
    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<String> cancelSupplyOrdersForDistributorOnDate(
            @RequestParam UUID distributorId,
            @RequestParam String date) {
        LocalDate orderDate = LocalDate.parse(date);
        int cancelledCount = supplyOrderServe.cancelSupplyOrdersForDistributorOnDate(distributorId, orderDate);
        return new ResponseEntity<>(cancelledCount + " supply orders cancelled", HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSupplyOrderById(@PathVariable UUID id) {
        Optional<SupplyOrder> supplyOrder = supplyOrderServe.getSupplyOrderById(id);
        if (supplyOrder.isPresent()) {
            return new ResponseEntity<>(supplyOrder.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The supply order with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSupplyOrder(@PathVariable UUID id, @RequestBody SupplyOrder supplyOrder) {
        String returnedMessage = supplyOrderServe.updateSupplyOrder(id, supplyOrder);
        if (returnedMessage.equals("The supply order with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> updateSupplyOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        String returnedMessage = supplyOrderServe.updateSupplyOrderStatus(id, status);
        if (returnedMessage.equals("The supply order with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else if (returnedMessage.equals("Supply order status updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteSupplyOrder(@PathVariable UUID id) {
        String returnedMessage = supplyOrderServe.deleteSupplyOrder(id);
        if (returnedMessage.equals("The supply order with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else if (returnedMessage.equals("That supply order has delivery batches, delete them first")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
