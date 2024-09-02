package com.coupons.controller;


import com.coupons.model.Item;
import com.coupons.model.ItemWrapper;
import com.coupons.model.coupon1;
import com.coupons.service.CouponService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/createCoupon")
    public ResponseEntity<coupon1> createCoupon(@RequestBody coupon1 coupon)  {
        return ResponseEntity.ok(couponService.createCoupon(coupon));
    }

    @GetMapping("/getAllCoupons")
    public ResponseEntity<List<coupon1>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @PostMapping("/getCoupon/applicable-coupons")
    public ResponseEntity<?> getApplicableCoupons(@RequestBody ItemWrapper item) throws JsonProcessingException {
//        ObjectMapper objectMapper=new ObjectMapper();
//        String s1 = objectMapper.writeValueAsString(item);
        return ResponseEntity.ok(couponService.getApplicableCoupons(item));
    }
//here we can add extra method according to business requirement

    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<?> getUpdatedCart(@RequestBody ItemWrapper item,@PathVariable int id) throws JsonProcessingException {
//        ObjectMapper objectMapper=new ObjectMapper();
//        String s1 = objectMapper.writeValueAsString(item);
        return ResponseEntity.ok(couponService.getCartResponse(item,id));
    }

}
