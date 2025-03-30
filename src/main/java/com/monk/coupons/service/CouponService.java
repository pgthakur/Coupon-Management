package com.monk.coupons.service;

import com.monk.coupons.dto.CouponCreationRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    public void createCoupon(CouponCreationRequestDTO couponCreationRequestDTO){
        String coupon_type = couponCreationRequestDTO.getType();
        switch (coupon_type.toLowerCase()) {
            case "cart-wise" -> createCouponCartWise(couponCreationRequestDTO);
            case "product-wise" -> createCouponProductWise(couponCreationRequestDTO);
            case "bxgy" -> createCouponBuyGet(couponCreationRequestDTO);
        }
    }

    private void createCouponBuyGet(CouponCreationRequestDTO couponCreationRequestDTO) {

    }

    private void createCouponProductWise(CouponCreationRequestDTO couponCreationRequestDTO) {
    }

    private void createCouponCartWise(CouponCreationRequestDTO couponCreationRequestDTO) {

    }

}
