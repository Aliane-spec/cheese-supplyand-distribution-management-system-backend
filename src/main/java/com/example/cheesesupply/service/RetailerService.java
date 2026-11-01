package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.Retailer;
import com.example.cheesesupply.repository.DistributorRepository;
import com.example.cheesesupply.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class RetailerService {
    @Autowired
    private RetailerRepository retailerRepo;
    @Autowired
    private DistributorRepository distributorRepo;
    public String saveRetailer(Retailer retailer) {
        retailerRepo.save(retailer);
        return "Retailer saved successfully";
    }
    public List<Retailer> getAllRetailers() {
        return retailerRepo.findAll();
    }
    public List<Retailer> getRetailersByOwnerName(String ownerName) {
        return retailerRepo.findByOwnerNameIgnoreCaseOrderByShopNameAsc(ownerName);
    }
    public List<Retailer> getRetailersOfDistributor(UUID distributorId) {
        return retailerRepo.findByDistributorId(distributorId);
    }
    public boolean distributorExists(UUID distributorId) {
        return distributorRepo.existsById(distributorId);
    }
    public Optional<Retailer> getRetailerById(UUID id) {
        return retailerRepo.findById(id);
    }
    public String updateRetailer(UUID id, Retailer retailer) {
        if (retailerRepo.findById(id).isEmpty()) {
            return "The retailer with that id does not exist";
        }
        retailer.setId(id);
        retailerRepo.save(retailer);
        return "Retailer updated successfully";
    }
    public String deleteRetailer(UUID id) {
        if (retailerRepo.findById(id).isEmpty()) {
            return "The retailer with that id does not exist";
        }
        retailerRepo.deleteById(id);
        return "Retailer deleted successfully";
    }
}