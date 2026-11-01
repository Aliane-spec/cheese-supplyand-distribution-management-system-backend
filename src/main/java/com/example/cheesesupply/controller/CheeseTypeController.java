package com.example.cheesesupply.controller;

import com.example.cheesesupply.domain.CheeseType;
import com.example.cheesesupply.service.CheeseTypeService;
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
@RequestMapping(value = "/api/cheese-types")
public class CheeseTypeController {
    @Autowired
    private CheeseTypeService cheeseTypeServe;
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCheeseType(@RequestBody CheeseType cheeseType) {
        String returnedMessage = cheeseTypeServe.saveCheeseType(cheeseType);
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCheeseTypes() {
        return new ResponseEntity<>(cheeseTypeServe.getAllCheeseTypes(), HttpStatus.OK);
    }
    @GetMapping(value = "/unused")
    public ResponseEntity<?> getUnusedCheeseTypes() {
        return new ResponseEntity<>(cheeseTypeServe.getUnusedCheeseTypes(), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCheeseTypeById(@PathVariable UUID id) {
        Optional<CheeseType> cheeseType = cheeseTypeServe.getCheeseTypeById(id);
        if (cheeseType.isPresent()) {
            return new ResponseEntity<>(cheeseType.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The cheese type with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCheeseType(@PathVariable UUID id, @RequestBody CheeseType cheeseType) {
        String returnedMessage = cheeseTypeServe.updateCheeseType(id, cheeseType);
        if (returnedMessage.equals("The cheese type with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCheeseType(@PathVariable UUID id) {
        String returnedMessage = cheeseTypeServe.deleteCheeseType(id);
        if (returnedMessage.equals("The cheese type with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
    }
}
