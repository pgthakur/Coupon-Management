package com.monk.coupons.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CouponCreationRequestDTO {
    private String type;  // cart-wise, product-wise, bxgy
    private CouponDetails details;
}
