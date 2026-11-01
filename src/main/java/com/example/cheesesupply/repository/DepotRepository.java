package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface DepotRepository extends JpaRepository<Depot, UUID> {
    @Query("SELECT d FROM Depot d WHERE NOT EXISTS (SELECT dist.id FROM Distributor dist WHERE dist.depot = d) ORDER BY d.depotNumber ASC")
    List<Depot> findFreeDepots();
    Optional<Depot> findByDepotNumber(int depotNumber);
}