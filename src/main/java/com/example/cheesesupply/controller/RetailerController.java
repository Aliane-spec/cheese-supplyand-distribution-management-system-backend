package com.example.cheesesupply.controller;
import com.example.cheesesupply.domain.Retailer;
import com.example.cheesesupply.service.RetailerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/retailers")
public class RetailerController {
    @Autowired
    private RetailerService retailerServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveRetailer(@RequestBody Retailer retailer) {
        String returnedMessage = retailerServe.saveRetailer(retailer);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllRetailers() {
        return new ResponseEntity<>(retailerServe.getAllRetailers(), HttpStatus.OK);
    }
    @GetMapping(value = "/by-owner-name")
    public ResponseEntity<?> getRetailersByOwnerName(@RequestParam String ownerName) {
        return new ResponseEntity<>(retailerServe.getRetailersByOwnerName(ownerName), HttpStatus.OK);
    }
    @GetMapping(value = "/of-distributor/{distributorId}")
    public ResponseEntity<?> getRetailersOfDistributor(@PathVariable UUID distributorId) {
        if (!retailerServe.distributorExists(distributorId)) {
            return new ResponseEntity<>("The distributor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(retailerServe.getRetailersOfDistributor(distributorId), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRetailerById(@PathVariable UUID id) {
        Optional<Retailer> retailer = retailerServe.getRetailerById(id);
        if (retailer.isPresent()) {
            return new ResponseEntity<>(retailer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The retailer with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateRetailer(@PathVariable UUID id, @RequestBody Retailer retailer) {
        String returnedMessage = retailerServe.updateRetailer(id, retailer);
        if (returnedMessage.equals("The retailer with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteRetailer(@PathVariable UUID id) {
        String returnedMessage = retailerServe.deleteRetailer(id);
        if (returnedMessage.equals("The retailer with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
