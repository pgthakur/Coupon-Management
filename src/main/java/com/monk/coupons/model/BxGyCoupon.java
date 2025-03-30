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
        double totalDiscountAmount = 0;

        // Count how many qualified "buy" products are in the cart
        for (CartItem item : cart.getItems()) {
            if (buyProductIds.contains(item.getProductId())) {
                buyCount += item.getQuantity();
            }
        }

        // Calculate how many times the buy condition is satisfied
        int applicableSets = Math.min(buyCount / buyQuantity, repetitionLimit);

        if (applicableSets <= 0) {
            return 0;
        }

        // Calculate the discount for each "get" product
        for (CartItem item : cart.getItems()) {
            if (getProductIds.contains(item.getProductId())) {
                // Calculate how many items qualify for the discount
                int discountItems = Math.min(applicableSets * getQuantity, item.getQuantity());
                totalDiscountAmount += discountItems * item.getPrice();
            }
        }

        return totalDiscountAmount;
    }
}