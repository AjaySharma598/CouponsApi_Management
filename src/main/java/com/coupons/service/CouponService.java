package com.coupons.service;

import com.coupons.model.*;

import java.util.List;
import java.util.Map;

public interface CouponService {
    public List<coupon1> getAllCoupons();

    coupon1 createCoupon(coupon1 coupon);

    Map<String, List<CouponDetails>> getApplicableCoupons(ItemWrapper item);

    UpdatedCartWrapper getCartResponse(ItemWrapper item,int id);

}
