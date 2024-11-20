package com.monkcommon.CouponAPI.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "total_price")
    private Double totalPrice = 0.0;

    @Column(name = "total_discount")
    private Double totalDiscount = 0.0;

    @Column(name = "final_price")
    private Double finalPrice = 0.0;

    @Column(name = "discount")
    private Double discount = 0.0;

    // Explicitly added methods to resolve resolution issues
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return this.items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    // Additional utility methods
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void applyDiscount(Double discountPercentage) {
        this.totalDiscount = this.totalPrice * (discountPercentage / 100);
        this.finalPrice = this.totalPrice - this.totalDiscount;
    }

    public void setDiscount(double discount) {
        this.discount=discount;

    }
}