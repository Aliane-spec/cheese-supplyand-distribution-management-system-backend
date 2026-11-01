package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.DeliveryBatch;
import com.example.cheesesupply.domain.OrderStatus;
import com.example.cheesesupply.domain.SupplyOrder;
import com.example.cheesesupply.repository.DeliveryBatchRepository;
import com.example.cheesesupply.repository.SupplyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class DeliveryBatchService {
    @Autowired
    private DeliveryBatchRepository deliveryBatchRepo;
    @Autowired
    private SupplyOrderRepository supplyOrderRepo;
    public String saveDeliveryBatch(UUID supplyOrderId, DeliveryBatch deliveryBatch) {
        Optional<SupplyOrder> supplyOrder = supplyOrderRepo.findById(supplyOrderId);
        if (supplyOrder.isEmpty()) {
            return "The supply order with that id does not exist";
        }
        if (supplyOrder.get().getStatus() != OrderStatus.DELIVERED) {
            return "Delivery batches are only for DELIVERED supply orders";
        }
        deliveryBatch.setId(null);
        deliveryBatch.setDeliveredDate(LocalDate.now());
        deliveryBatch.setSupplyOrder(supplyOrder.get());
        deliveryBatchRepo.save(deliveryBatch);
        return "Delivery batch saved successfully";
    }
    public List<DeliveryBatch> getDeliveryBatchesByRetailer(String licenseNumber) {
        return deliveryBatchRepo.findByRetailerLicenseNumberOrderByDeliveredDateDesc(licenseNumber);
    }
    public Optional<DeliveryBatch> getDeliveryBatchById(UUID id) {
        return deliveryBatchRepo.findById(id);
    }
    public String deleteDeliveryBatch(UUID id) {
        if (deliveryBatchRepo.findById(id).isEmpty()) {
            return "The delivery batch with that id does not exist";
        }
        deliveryBatchRepo.deleteById(id);
        return "Delivery batch deleted successfully";
    }
}