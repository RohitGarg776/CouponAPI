package com.monkcommon.CouponAPI.repository;


import com.monkcommon.CouponAPI.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByType(Coupon.CouponType type);
}