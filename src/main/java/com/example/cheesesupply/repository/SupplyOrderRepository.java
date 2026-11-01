package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.OrderStatus;
import com.example.cheesesupply.domain.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, UUID> {
    List<SupplyOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);
    List<SupplyOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
    boolean existsByDistributorIdAndOrderDateAndStatusNot(UUID distributorId, LocalDate orderDate, OrderStatus status);
    long countByStatus(OrderStatus status);
    long countByOrderDate(LocalDate orderDate);
    @Query("SELECT s.status, COUNT(s) FROM SupplyOrder s GROUP BY s.status")
    List<Object[]> countSupplyOrdersByStatus();
    @Modifying
    @Query("UPDATE SupplyOrder s SET s.status = :newStatus WHERE s.distributor.id = :distributorId AND s.orderDate = :date AND s.status <> :excludedStatus")
    int cancelOrdersForDistributorOnDate(
            @Param("distributorId") UUID distributorId,
            @Param("date") LocalDate date,
            @Param("newStatus") OrderStatus newStatus,
            @Param("excludedStatus") OrderStatus excludedStatus);
}