package com.monk.coupons.repository;

import com.monk.coupons.model.BaseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<BaseCoupon, Long> {
}
