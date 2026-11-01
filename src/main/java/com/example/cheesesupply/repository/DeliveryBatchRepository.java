package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.DeliveryBatch;
import com.example.cheesesupply.domain.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
public interface DeliveryBatchRepository extends JpaRepository<DeliveryBatch, UUID> {
    @Query("SELECT d FROM DeliveryBatch d WHERE d.supplyOrder.retailer.licenseNumber = :licenseNumber ORDER BY d.deliveredDate DESC")
    List<DeliveryBatch> findByRetailerLicenseNumberOrderByDeliveredDateDesc(
            @Param("licenseNumber") String licenseNumber);
    boolean existsBySupplyOrder(SupplyOrder supplyOrder);
}