package com.monk.coupons.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CartWiseCoupon extends BaseCoupon {

    private double threshold;
    private double discountPercent;

    @Override
    public double applyDiscount(Cart cart) {
        if (cart.getTotalAmount() >= threshold) {
            return cart.getTotalAmount() * (discountPercent / 100);
        }
        return 0;
    }
}