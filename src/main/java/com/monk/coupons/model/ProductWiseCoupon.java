package com.monk.coupons.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ProductWiseCoupon extends BaseCoupon {

    private Long productId;
    private double discountPercent;

    @Override
    public double applyDiscount(Cart cart) {
        return cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .mapToDouble(item -> item.getPrice() * item.getQuantity() * (discountPercent / 100))
                .sum();
    }
}