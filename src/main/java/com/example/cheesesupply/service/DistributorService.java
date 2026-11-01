package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.Distributor;
import com.example.cheesesupply.domain.CheeseType;
import com.example.cheesesupply.domain.Depot;
import com.example.cheesesupply.repository.CheeseTypeRepository;
import com.example.cheesesupply.repository.DepotRepository;
import com.example.cheesesupply.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class DistributorService {
    @Autowired
    private DistributorRepository distributorRepo;
    @Autowired
    private CheeseTypeRepository cheeseTypeRepo;
    @Autowired
    private DepotRepository depotRepo;
    public String saveDistributor(Distributor distributor) {
        distributorRepo.save(distributor);
        return "Distributor saved successfully";
    }
    public List<Distributor> getAllDistributors() {
        return distributorRepo.findAll();
    }
    public List<Distributor> getDistributorsByCheeseType(String name) {
        return distributorRepo.findByCheeseTypeNameIgnoreCase(name);
    }
    public String addCheeseType(UUID distributorId, UUID cheeseTypeId) {
        Optional<Distributor> distributor = distributorRepo.findById(distributorId);
        if (distributor.isEmpty()) {
            return "The distributor with that id does not exist";
        }
        Optional<CheeseType> cheeseType = cheeseTypeRepo.findById(cheeseTypeId);
        if (cheeseType.isEmpty()) {
            return "That cheese type does not exist";
        }
        if (distributor.get().getCheeseTypes().stream()
                .anyMatch(existingCheeseType -> existingCheeseType.getId().equals(cheeseTypeId))) {
            return "The distributor already has that cheese type";
        }
        distributor.get().getCheeseTypes().add(cheeseType.get());
        distributorRepo.save(distributor.get());
        return "Cheese type added to the distributor";
    }
    public String changeDepot(UUID distributorId, int depotNumber) {
        Optional<Distributor> distributor = distributorRepo.findById(distributorId);
        if (distributor.isEmpty()) {
            return "The distributor with that id does not exist";
        }
        Optional<Depot> depot = depotRepo.findByDepotNumber(depotNumber);
        if (depot.isEmpty()) {
            return "The depot with that depotNumber does not exist";
        }
        if (distributorRepo.existsByDepotIdAndIdNot(depot.get().getId(), distributorId)) {
            return "That depot is taken by another distributor";
        }
        distributor.get().setDepot(depot.get());
        distributorRepo.save(distributor.get());
        return "Depot changed successfully";
    }
    public Optional<Distributor> getDistributorById(UUID id) {
        return distributorRepo.findById(id);
    }
    public String updateDistributor(UUID id, Distributor distributor) {
        if (distributorRepo.findById(id).isEmpty()) {
            return "The distributor with that id does not exist";
        }
        distributor.setId(id);
        distributorRepo.save(distributor);
        return "Distributor updated successfully";
    }
    public String deleteDistributor(UUID id) {
        if (distributorRepo.findById(id).isEmpty()) {
            return "The distributor with that id does not exist";
        }
        distributorRepo.deleteById(id);
        return "Distributor deleted successfully";
    }
}