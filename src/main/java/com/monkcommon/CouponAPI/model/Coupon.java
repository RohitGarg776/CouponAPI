package com.monkcommon.CouponAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public void setApplicableProductIds(List<Long> applicableProductIds) {
        this.applicableProductIds = applicableProductIds;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @ElementCollection
    @CollectionTable(name = "coupon_applicable_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    private List<Long> applicableProductIds;

    @Column(nullable = true)
    private Double threshold = 0.0;

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage = 0.0;

    // Add getters for specific methods
    public CouponType getType() {
        return this.type;
    }

    public List<Long> getApplicableProductIds() {
        return this.applicableProductIds;
    }

    public Double getThreshold() {
        return this.threshold;
    }

    public Double getDiscountPercentage() {
        return this.discountPercentage;
    }

    public enum CouponType {
        CART_WISE, PRODUCT_WISE, BXGY
    }
}