package com.monk.coupons.service;

import com.monk.coupons.dto.*;
import com.monk.coupons.model.*;
import com.monk.coupons.repository.CouponRepository;
import com.monk.coupons.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CartRepository cartRepository;

    public BaseCoupon createCoupon(CouponCreationRequestDTO couponCreationRequestDTO) {
        String couponType = couponCreationRequestDTO.getType().toLowerCase();
        BaseCoupon coupon;

        switch (couponType) {
            case "cart-wise" -> coupon = createCouponCartWise(couponCreationRequestDTO);
            case "product-wise" -> coupon = createCouponProductWise(couponCreationRequestDTO);
            case "bxgy" -> coupon = createCouponBuyGet(couponCreationRequestDTO);
            default -> throw new IllegalArgumentException("Invalid coupon type: " + couponType);
        }

        return couponRepository.save(coupon);
    }

    private BaseCoupon createCouponBuyGet(CouponCreationRequestDTO couponCreationRequestDTO) {
        BxGyCoupon coupon = new BxGyCoupon();
        coupon.setType(couponCreationRequestDTO.getType());

        CouponDetails details = couponCreationRequestDTO.getDetails();

        List<Long> buyProductIds = details.getBuyProducts().stream()
                .map(ProductQuantityDTO::getProductId)
                .collect(Collectors.toList());

        List<Long> getProductIds = details.getGetProducts().stream()
                .map(ProductQuantityDTO::getProductId)
                .collect(Collectors.toList());

        coupon.setBuyProductIds(buyProductIds);
        coupon.setGetProductIds(getProductIds);

        // Assuming the first product in the list determines the quantity
        coupon.setBuyQuantity(details.getBuyProducts().get(0).getQuantity());
        coupon.setGetQuantity(details.getGetProducts().get(0).getQuantity());

        coupon.setRepetitionLimit(details.getRepetitionLimit() != null ?
                details.getRepetitionLimit() : 1);

        coupon.setDescription("Buy " + coupon.getBuyQuantity() +
                " of specified products, get " + coupon.getGetQuantity() +
                " of specified products");

        return coupon;
    }

    private BaseCoupon createCouponProductWise(CouponCreationRequestDTO couponCreationRequestDTO) {
        ProductWiseCoupon coupon = new ProductWiseCoupon();
        coupon.setType(couponCreationRequestDTO.getType());

        CouponDetails details = couponCreationRequestDTO.getDetails();
        coupon.setProductId(details.getProductId());
        coupon.setDiscountPercent(details.getDiscount());

        coupon.setDescription(details.getDiscount() + "% off on product ID " + details.getProductId());

        return coupon;
    }

    private BaseCoupon createCouponCartWise(CouponCreationRequestDTO couponCreationRequestDTO) {
        CartWiseCoupon coupon = new CartWiseCoupon();
        coupon.setType(couponCreationRequestDTO.getType());

        CouponDetails details = couponCreationRequestDTO.getDetails();
        coupon.setThreshold(details.getThreshold());
        coupon.setDiscountPercent(details.getDiscount());

        coupon.setDescription(details.getDiscount() + "% off on cart total when above " + details.getThreshold());

        return coupon;
    }

    public List<BaseCoupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public BaseCoupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    public Cart createCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();

        for (CartItemDTO itemDTO : cartDTO.getItems()) {
            CartItem item = new CartItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            cartItems.add(item);
        }

        cart.setItems(cartItems);
        return cartRepository.save(cart);
    }

    public ApplicableCouponResponseDTO getApplicableCoupons(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        List<BaseCoupon> allCoupons = couponRepository.findAll();
        List<ApplicableCouponDTO> applicableCoupons = new ArrayList<>();

        for (BaseCoupon coupon : allCoupons) {
            double discount = coupon.applyDiscount(cart);
            if (discount > 0) {
                ApplicableCouponDTO dto = new ApplicableCouponDTO();
                dto.setCouponId(coupon.getId());
                dto.setType(coupon.getType());
                dto.setDiscount(discount);
                applicableCoupons.add(dto);
            }
        }

        ApplicableCouponResponseDTO response = new ApplicableCouponResponseDTO();
        response.setApplicableCoupons(applicableCoupons);
        return response;
    }
}