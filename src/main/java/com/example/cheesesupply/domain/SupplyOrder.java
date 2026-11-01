package com.example.cheesesupply.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "supply_order")
public class SupplyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int quantityKg;
    @ManyToOne
    @JoinColumn(name = "retailer_id")
    private Retailer retailer;
    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public int getQuantityKg() {
        return quantityKg;
    }
    public void setQuantityKg(int quantityKg) {
        this.quantityKg = quantityKg;
    }
    public Retailer getRetailer() {
        return retailer;
    }
    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }
    public Distributor getDistributor() {
        return distributor;
    }
    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }
}