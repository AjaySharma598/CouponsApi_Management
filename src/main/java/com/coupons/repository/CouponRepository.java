package com.coupons.repository;

import com.coupons.model.coupon1;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends MongoRepository<coupon1, String> {
}
