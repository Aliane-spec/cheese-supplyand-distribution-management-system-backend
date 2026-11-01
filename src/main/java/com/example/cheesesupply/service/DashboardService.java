package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.OrderStatus;
import com.example.cheesesupply.repository.DeliveryBatchRepository;
import com.example.cheesesupply.repository.DistributorRepository;
import com.example.cheesesupply.repository.RetailerRepository;
import com.example.cheesesupply.repository.SupplyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class DashboardService {
    @Autowired
    private RetailerRepository retailerRepo;
    @Autowired
    private DistributorRepository distributorRepo;
    @Autowired
    private DepotService depotServe;
    @Autowired
    private SupplyOrderRepository supplyOrderRepo;
    @Autowired
    private DeliveryBatchRepository deliveryBatchRepo;
    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("retailers", retailerRepo.count());
        summary.put("distributors", distributorRepo.count());
        summary.put("freeDepots", (long) depotServe.getFreeDepots().size());
        summary.put("ordersToday", supplyOrderRepo.countByOrderDate(LocalDate.now()));
        summary.put("placed", supplyOrderRepo.countByStatus(OrderStatus.PLACED));
        summary.put("batches", deliveryBatchRepo.count());
        return summary;
    }
}