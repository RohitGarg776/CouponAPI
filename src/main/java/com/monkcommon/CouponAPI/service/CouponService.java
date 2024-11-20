package com.monkcommon.CouponAPI.service;

import com.monkcommon.CouponAPI.model.Cart;
import com.monkcommon.CouponAPI.model.Coupon;
import com.monkcommon.CouponAPI.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // Create a new coupon
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    // Retrieve all coupons
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Retrieve a coupon by ID
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found with ID: " + id));
    }

    // Update an existing coupon
    @Transactional
    public Coupon updateCoupon(Long id, Coupon couponDetails) {
        Coupon existingCoupon = getCouponById(id);

        // Update fields
        existingCoupon.setType(couponDetails.getType());
        existingCoupon.setApplicableProductIds(couponDetails.getApplicableProductIds());
        existingCoupon.setThreshold(couponDetails.getThreshold());
        existingCoupon.setDiscountPercentage(couponDetails.getDiscountPercentage());


        return couponRepository.save(existingCoupon);
    }

    // Delete a coupon by ID
    public void deleteCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        couponRepository.delete(coupon);
    }

    // Find applicable coupons for a given cart
    public List<Coupon> findApplicableCoupons(Cart cart) {
        return couponRepository.findAll().stream()
                .filter(coupon -> isCouponApplicable(coupon, cart))
                .collect(Collectors.toList());
    }

    private boolean isCouponApplicable(Coupon coupon, Cart cart) {
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            return false;
        }

        switch (coupon.getType()) {
            case CART_WISE:
                return cart.getTotalPrice() >= coupon.getThreshold();
            case PRODUCT_WISE:
                return cart.getItems().stream()
                        .anyMatch(item -> coupon.getApplicableProductIds().contains(item.getProductId()));
            case BXGY:
                return cart.getItems().stream()
                        .filter(item -> coupon.getApplicableProductIds().contains(item.getProductId()))
                        .count() >= 2;
            default:
                return false;
        }
    }

    // Apply a coupon to a cart
    @Transactional
    public Cart applyCoupon(Cart cart, Long couponId) {
        Coupon coupon = getCouponById(couponId);

        if (!isCouponApplicable(coupon, cart)) {
            throw new IllegalArgumentException("Coupon is not applicable to this cart");
        }

        double discount = 0.0;
        switch (coupon.getType()) {
            case CART_WISE:
                discount = cart.getTotalPrice() * (coupon.getDiscountPercentage() / 100.0);
                break;
            case PRODUCT_WISE:
                discount = cart.getItems().stream()
                        .filter(item -> coupon.getApplicableProductIds().contains(item.getProductId()))
                        .mapToDouble(item -> item.getPrice() * (coupon.getDiscountPercentage() / 100.0))
                        .sum();
                break;
            case BXGY:
                // Implement Buy X Get Y logic if needed
                break;
        }

        cart.setDiscount(discount);
        cart.setFinalPrice(cart.getTotalPrice() - discount);
        return cart;
    }
}
