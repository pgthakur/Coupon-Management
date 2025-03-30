package com.monk.coupons.controller;

import com.monk.coupons.dto.CouponCreationRequestDTO;
import com.monk.coupons.model.BaseCoupon;
import com.monk.coupons.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/Coupons")
    public ResponseEntity<String> createCoupon(@RequestBody CouponCreationRequestDTO couponCreationRequestDTO) {
        couponService.createCoupon(couponCreationRequestDTO);
        return new ResponseEntity<>("Coupon Created Succesfully", HttpStatus.CREATED);
    }
}