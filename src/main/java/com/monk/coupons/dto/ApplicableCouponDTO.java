package com.monk.coupons.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicableCouponDTO {
    private Long couponId;
    private String type;       // cart-wise, product-wise, bxgy
    private double discount;   // discount amount
}
