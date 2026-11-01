package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.Retailer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
public interface RetailerRepository extends JpaRepository<Retailer, UUID> {
    List<Retailer> findByOwnerNameIgnoreCaseOrderByShopNameAsc(String ownerName);
    @Query("SELECT DISTINCT s.retailer FROM SupplyOrder s WHERE s.distributor.id = :distributorId")
    List<Retailer> findByDistributorId(@Param("distributorId") UUID distributorId);
}
