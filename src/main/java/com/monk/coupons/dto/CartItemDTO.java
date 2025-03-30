package com.monk.coupons.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long productId;
    private int quantity;
    private double price;
}
