package com.monk.coupons.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BxGyCoupon extends BaseCoupon {

    @ElementCollection
    private List<Long> buyProductIds;  // Buy X products

    @ElementCollection
    private List<Long> getProductIds;  // Get Y products

    private int buyQuantity;
    private int getQuantity;
    private int repetitionLimit;

    @Override
    public double applyDiscount(Cart cart) {
        int buyCount = 0;
        int freeCount = 0;

        for (CartItem item : cart.getItems()) {
            if (buyProductIds.contains(item.getProductId())) {
                buyCount += item.getQuantity();
            }
            if (getProductIds.contains(item.getProductId())) {
                freeCount += item.getQuantity();
            }
        }

        int applicableCount = Math.min(buyCount / buyQuantity, repetitionLimit);
        return applicableCount * freeCount;
    }
}