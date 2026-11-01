package com.example.cheesesupply.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "delivery_batch")
public class DeliveryBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cheeseName;
    private String note;
    private int quantityKg;
    private LocalDate deliveredDate;
    @ManyToOne
    @JoinColumn(name = "supply_order_id")
    private SupplyOrder supplyOrder;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getCheeseName() {
        return cheeseName;
    }
    public void setCheeseName(String cheeseName) {
        this.cheeseName = cheeseName;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public int getQuantityKg() {
        return quantityKg;
    }
    public void setQuantityKg(int quantityKg) {
        this.quantityKg = quantityKg;
    }
    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }
    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
    public SupplyOrder getSupplyOrder() {
        return supplyOrder;
    }
    public void setSupplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
    }
}
