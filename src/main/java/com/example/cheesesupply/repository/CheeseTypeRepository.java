package com.example.cheesesupply.repository;
import com.example.cheesesupply.domain.CheeseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;
public interface CheeseTypeRepository extends JpaRepository<CheeseType, UUID> {
    @Query("SELECT c FROM CheeseType c WHERE c.distributors IS EMPTY ORDER BY c.name ASC")
    List<CheeseType> findUnusedCheeseTypes();
}