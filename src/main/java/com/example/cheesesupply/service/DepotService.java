package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.Depot;
import com.example.cheesesupply.repository.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class DepotService {
    @Autowired
    private DepotRepository depotRepo;
    public String saveDepot(Depot depot) {
        depotRepo.save(depot);
        return "Depot saved successfully";
    }
    public List<Depot> getAllDepots() {
        return depotRepo.findAll();
    }
    public List<Depot> getFreeDepots() {
        return depotRepo.findFreeDepots();
    }
    public Optional<Depot> getDepotById(UUID id) {
        return depotRepo.findById(id);
    }
    public String updateDepot(UUID id, Depot depot) {
        if (depotRepo.findById(id).isEmpty()) {
            return "The depot with that id does not exist";
        }
        depot.setId(id);
        depotRepo.save(depot);
        return "Depot updated successfully";
    }
    public String deleteDepot(UUID id) {
        if (depotRepo.findById(id).isEmpty()) {
            return "The depot with that id does not exist";
        }
        depotRepo.deleteById(id);
        return "Depot deleted successfully";
    }
}