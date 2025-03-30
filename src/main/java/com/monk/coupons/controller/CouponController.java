package com.monk.coupons.controller;

import com.monk.coupons.dto.ApplicableCouponResponseDTO;
import com.monk.coupons.dto.CartDTO;
import com.monk.coupons.dto.CouponCreationRequestDTO;
import com.monk.coupons.model.BaseCoupon;
import com.monk.coupons.model.Cart;
import com.monk.coupons.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<BaseCoupon> createCoupon(@RequestBody CouponCreationRequestDTO couponCreationRequestDTO) {
        BaseCoupon createdCoupon = couponService.createCoupon(couponCreationRequestDTO);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<BaseCoupon>> getAllCoupons() {
        List<BaseCoupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<BaseCoupon> getCouponById(@PathVariable Long id) {
        BaseCoupon coupon = couponService.getCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        Cart cart = couponService.createCart(cartDTO);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @GetMapping("/carts/{cartId}/applicable-coupons")
    public ResponseEntity<ApplicableCouponResponseDTO> getApplicableCoupons(@PathVariable Long cartId) {
        ApplicableCouponResponseDTO applicableCoupons = couponService.getApplicableCoupons(cartId);
        return ResponseEntity.ok(applicableCoupons);
    }
}