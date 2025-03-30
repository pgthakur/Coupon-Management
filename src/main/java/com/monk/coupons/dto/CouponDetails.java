package com.monk.coupons.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponDetails {

    // For Cart-wise
    private Double threshold;
    private Double discount;

    // For Product-wise
    private Long productId;

    // For BxGy
    private List<ProductQuantityDTO> buyProducts;
    private List<ProductQuantityDTO> getProducts;
    private Integer repetitionLimit;
}