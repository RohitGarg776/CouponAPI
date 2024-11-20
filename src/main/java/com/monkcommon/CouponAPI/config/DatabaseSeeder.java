package com.monkcommon.CouponAPI.config;

import com.monkcommon.CouponAPI.model.Coupon;
import com.monkcommon.CouponAPI.repository.CouponRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(CouponRepository couponRepository) {
        return args -> {
            // Insert CART-WISE coupon
            Coupon cartWiseCoupon = new Coupon();
            cartWiseCoupon.setType(Coupon.CouponType.CART_WISE);
            cartWiseCoupon.setThreshold(100.0);
            cartWiseCoupon.setDiscountPercentage(10.0);
            couponRepository.save(cartWiseCoupon);

            // Insert PRODUCT-WISE coupon
            Coupon productWiseCoupon = new Coupon();
            productWiseCoupon.setType(Coupon.CouponType.PRODUCT_WISE);
            productWiseCoupon.setApplicableProductIds(Arrays.asList(1L)); // Product ID 1
            productWiseCoupon.setDiscountPercentage(20.0);
            couponRepository.save(productWiseCoupon);

            // Insert BXGY coupon
            Coupon bxgyCoupon = new Coupon();
            bxgyCoupon.setType(Coupon.CouponType.BXGY);
            bxgyCoupon.setApplicableProductIds(Arrays.asList(1L, 2L, 3L)); // Products involved in BXGY
            bxgyCoupon.setThreshold(2.0); // Repetition limit
            couponRepository.save(bxgyCoupon);

            System.out.println("Database initialized with test coupons.");
        };
    }
}
