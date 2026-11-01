package com.example.cheesesupply.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
@Entity
@Table(name = "depot")
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int depotNumber;
    private String name;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public int getDepotNumber() {
        return depotNumber;
    }
    public void setDepotNumber(int depotNumber) {
        this.depotNumber = depotNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
