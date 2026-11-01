package com.example.cheesesupply.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "distributor")
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String companyName;
    private String contactName;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;
    @ManyToMany
    @JoinTable(
            name = "distributor_cheese_type",
            joinColumns = @JoinColumn(name = "distributor_id"),
            inverseJoinColumns = @JoinColumn(name = "cheese_type_id")
    )
    private List<CheeseType> cheeseTypes = new ArrayList<>();
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Depot getDepot() {
        return depot;
    }
    public void setDepot(Depot depot) {
        this.depot = depot;
    }
    public List<CheeseType> getCheeseTypes() {
        return cheeseTypes;
    }
    public void setCheeseTypes(List<CheeseType> cheeseTypes) {
        this.cheeseTypes = cheeseTypes;
    }
}
