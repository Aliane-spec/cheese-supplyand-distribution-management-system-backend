package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.SupplyOrder;
import com.example.cheesesupply.domain.OrderStatus;
import com.example.cheesesupply.repository.DeliveryBatchRepository;
import com.example.cheesesupply.repository.SupplyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SupplyOrderService {
    @Autowired
    private SupplyOrderRepository supplyOrderRepo;
    @Autowired
    private DeliveryBatchRepository deliveryBatchRepo;
    public String saveSupplyOrder(SupplyOrder supplyOrder) {
        if (supplyOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Supply order date cannot be in the past";
        }
        if (supplyOrderRepo.existsByDistributorIdAndOrderDateAndStatusNot(
                supplyOrder.getDistributor().getId(), supplyOrder.getOrderDate(), OrderStatus.REJECTED)) {
            return "Distributor already has an order on that date";
        }
        supplyOrder.setStatus(OrderStatus.PLACED);
        supplyOrderRepo.save(supplyOrder);
        return "Supply order saved successfully";
    }
    public List<SupplyOrder> getAllSupplyOrders() {
        return supplyOrderRepo.findAll();
    }
    public Page<SupplyOrder> getSupplyOrdersPage(Pageable pageable) {
        return supplyOrderRepo.findAll(pageable);
    }
    public List<SupplyOrder> getSupplyOrdersByStatus(OrderStatus status) {
        return supplyOrderRepo.findByStatusOrderByOrderDateAsc(status);
    }
    public List<SupplyOrder> getSupplyOrdersBetween(LocalDate start, LocalDate end) {
        return supplyOrderRepo.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }
    public List<Object[]> getSupplyOrderStatsByStatus() {
        return supplyOrderRepo.countSupplyOrdersByStatus();
    }
    @Transactional
    public int cancelSupplyOrdersForDistributorOnDate(UUID distributorId, LocalDate date) {
        return supplyOrderRepo.cancelOrdersForDistributorOnDate(
                distributorId, date, OrderStatus.REJECTED, OrderStatus.DELIVERED);
    }
    public Optional<SupplyOrder> getSupplyOrderById(UUID id) {
        return supplyOrderRepo.findById(id);
    }
    public String updateSupplyOrder(UUID id, SupplyOrder supplyOrder) {
        Optional<SupplyOrder> existing = supplyOrderRepo.findById(id);
        if (existing.isEmpty()) {
            return "The supply order with that id does not exist";
        }
        supplyOrder.setStatus(existing.get().getStatus());
        supplyOrder.setId(id);
        supplyOrderRepo.save(supplyOrder);
        return "Supply order updated successfully";
    }
    public String updateSupplyOrderStatus(UUID id, OrderStatus newStatus) {
        Optional<SupplyOrder> existing = supplyOrderRepo.findById(id);
        if (existing.isEmpty()) {
            return "The supply order with that id does not exist";
        }
        OrderStatus currentStatus = existing.get().getStatus();
        boolean validTransition = currentStatus == OrderStatus.PLACED
                && (newStatus == OrderStatus.DISPATCHED || newStatus == OrderStatus.REJECTED)
                || currentStatus == OrderStatus.DISPATCHED
                && (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.REJECTED);
        if (!validTransition) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
        existing.get().setStatus(newStatus);
        supplyOrderRepo.save(existing.get());
        return "Supply order status updated successfully";
    }
    public String deleteSupplyOrder(UUID id) {
        Optional<SupplyOrder> supplyOrder = supplyOrderRepo.findById(id);
        if (supplyOrder.isEmpty()) {
            return "The supply order with that id does not exist";
        }
        if (deliveryBatchRepo.existsBySupplyOrder(supplyOrder.get())) {
            return "That supply order has delivery batches, delete them first";
        }
        supplyOrderRepo.deleteById(id);
        return "Supply order deleted successfully";
    }
}