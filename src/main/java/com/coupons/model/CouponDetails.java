package com.coupons.model;

public class CouponDetails {
    private int couponId;
    private String type;
    private double discount;

    public CouponDetails() {
    }

    public CouponDetails(int couponId, String type, double discount) {
        this.couponId = couponId;
        this.type = type;
        this.discount = discount;
    }

    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
