package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
public interface DistributorRepository extends JpaRepository<Distributor, UUID> {
    @Query("SELECT DISTINCT d FROM Distributor d JOIN d.cheeseTypes ct WHERE LOWER(ct.name) = LOWER(:name)")
    List<Distributor> findByCheeseTypeNameIgnoreCase(@Param("name") String name);
    boolean existsByDepotIdAndIdNot(UUID depotId, UUID id);
}