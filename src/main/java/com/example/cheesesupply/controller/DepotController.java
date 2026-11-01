package com.example.cheesesupply.controller;
import com.example.cheesesupply.domain.Depot;
import com.example.cheesesupply.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/depots")
public class DepotController {
    @Autowired
    private DepotService depotServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveDepot(@RequestBody Depot depot) {
        String returnedMessage = depotServe.saveDepot(depot);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllDepots() {
        return new ResponseEntity<>(depotServe.getAllDepots(), HttpStatus.OK);
    }
    @GetMapping(value = "/free")
    public ResponseEntity<?> getFreeDepots() {
        return new ResponseEntity<>(depotServe.getFreeDepots(), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDepotById(@PathVariable UUID id) {
        Optional<Depot> depot = depotServe.getDepotById(id);
        if (depot.isPresent()) {
            return new ResponseEntity<>(depot.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The depot with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateDepot(@PathVariable UUID id, @RequestBody Depot depot) {
        String returnedMessage = depotServe.updateDepot(id, depot);
        if (returnedMessage.equals("The depot with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDepot(@PathVariable UUID id) {
        String returnedMessage = depotServe.deleteDepot(id);
        if (returnedMessage.equals("The depot with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
