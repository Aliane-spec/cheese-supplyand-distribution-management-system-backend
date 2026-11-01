package com.example.cheesesupply.controller;
import com.example.cheesesupply.domain.Distributor;
import com.example.cheesesupply.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/distributors")
public class DistributorController {
    @Autowired
    private DistributorService distributorServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDistributor(@RequestBody Distributor distributor) {
        String returnedMessage = distributorServe.saveDistributor(distributor);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllDistributors() {
        return new ResponseEntity<>(distributorServe.getAllDistributors(), HttpStatus.OK);
    }
    @GetMapping(value = "/by-cheese-type")
    public ResponseEntity<?> getDistributorsByCheeseType(@RequestParam String name) {
        return new ResponseEntity<>(distributorServe.getDistributorsByCheeseType(name), HttpStatus.OK);
    }
    @PutMapping(value = "/{distributorId}/cheese-types/{cheeseTypeId}")
    public ResponseEntity<?> addCheeseType(
            @PathVariable UUID distributorId,
            @PathVariable UUID cheeseTypeId) {
        String returnedMessage = distributorServe.addCheeseType(distributorId, cheeseTypeId);
        if (returnedMessage.equals("The distributor with that id does not exist")
                || returnedMessage.equals("That cheese type does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else if (returnedMessage.equals("The distributor already has that cheese type")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @PatchMapping(value = "/{distributorId}/depot")
    public ResponseEntity<?> changeDepot(
            @PathVariable UUID distributorId,
            @RequestParam int depotNumber) {
        String returnedMessage = distributorServe.changeDepot(distributorId, depotNumber);
        if (returnedMessage.equals("The distributor with that id does not exist")
                || returnedMessage.equals("The depot with that depotNumber does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else if (returnedMessage.equals("That depot is taken by another distributor")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDistributorById(@PathVariable UUID id) {
        Optional<Distributor> distributor = distributorServe.getDistributorById(id);
        if (distributor.isPresent()) {
            return new ResponseEntity<>(distributor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The distributor with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateDistributor(@PathVariable UUID id, @RequestBody Distributor distributor) {
        String returnedMessage = distributorServe.updateDistributor(id, distributor);
        if (returnedMessage.equals("The distributor with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDistributor(@PathVariable UUID id) {
        String returnedMessage = distributorServe.deleteDistributor(id);
        if (returnedMessage.equals("The distributor with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
