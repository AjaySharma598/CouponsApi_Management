package com.coupons.model;

import java.util.List;

public class CartResponse {
    private List<CartItem> items;
    private double total_Price;
    private double total_Discount;
    private double final_Price;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(double total_Price) {
        this.total_Price = total_Price;
    }

    public double getTotal_Discount() {
        return total_Discount;
    }

    public void setTotal_Discount(double total_Discount) {
        this.total_Discount = total_Discount;
    }

    public double getFinal_Price() {
        return final_Price;
    }

    public void setFinal_Price(double final_Price) {
        this.final_Price = final_Price;
    }
}
