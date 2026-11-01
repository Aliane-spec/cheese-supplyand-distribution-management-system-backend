package com.example.cheesesupply.service;
import com.example.cheesesupply.domain.CheeseType;
import com.example.cheesesupply.repository.CheeseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CheeseTypeService {
    @Autowired
    private CheeseTypeRepository cheeseTypeRepo;
    public String saveCheeseType(CheeseType cheeseType) {
        cheeseTypeRepo.save(cheeseType);
        return "Cheese type saved successfully";
    }
    public List<CheeseType> getAllCheeseTypes() {
        return cheeseTypeRepo.findAll();
    }
    public List<CheeseType> getUnusedCheeseTypes() {
        return cheeseTypeRepo.findUnusedCheeseTypes();
    }
    public Optional<CheeseType> getCheeseTypeById(UUID id) {
        return cheeseTypeRepo.findById(id);
    }
    public String updateCheeseType(UUID id, CheeseType cheeseType) {
        if (cheeseTypeRepo.findById(id).isEmpty()) {
            return "The cheese type with that id does not exist";
        }
        cheeseType.setId(id);
        cheeseTypeRepo.save(cheeseType);
        return "Cheese type updated successfully";
    }
    public String deleteCheeseType(UUID id) {
        if (cheeseTypeRepo.findById(id).isEmpty()) {
            return "The cheese type with that id does not exist";
        }
        cheeseTypeRepo.deleteById(id);
        return "Cheese type deleted successfully";
    }
}