package com.coupons.service;
import com.coupons.model.*;
import com.coupons.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Override
    public List<coupon1> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public coupon1 createCoupon(coupon1 coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Map<String, List<CouponDetails>> getApplicableCoupons(ItemWrapper itemWrapper) {
        boolean product_flag=false;
        Item item = itemWrapper.getItem();   //if total cart greater than 100  and 20 % product
        int threshold = 0;  // We Will Get It Form Coupon Field Threshold From MongoDB Db
        int discount=0;
        List<Product> items = item.getItems();
        Map<Integer,Integer> productMap=new HashMap<>();

        String couponType="";
        List<CouponDetails> applicableCouponList = new ArrayList<>();
        Map<String, List<CouponDetails>> response = new HashMap<>(); // to contain response
//        ---------------------------------------------------------------

        List<coupon1> allCoupons = couponRepository.findAll();
        for (coupon1 allCoupon : allCoupons) {
            if (allCoupon.getType().equals("cart-wise")) {
                System.out.println("Service Layer : " + allCoupon);
                 couponType=allCoupon.getType();
                System.out.println(couponType);
              Object threshold1 = allCoupon.getDetails().get("threshold");
                System.out.println("threshold1 : "+threshold1);
                threshold = (Integer) threshold1;
                Object discount1 = allCoupon.getDetails().get("discount");
                discount=(Integer) discount1;
            }
        }
//        ----------------------------------------------------------------

        if (items != null  && !items.isEmpty()) {
//            -----------------------------------------------------------------
            for (Product product : items) {
                int productId = product.getProduct_id();
                System.out.println("Product-id : "+productId);
                for (coupon1 allCoupon : allCoupons) {
                    if (allCoupon.getType().equals("product-wise")) {
                        Map<String, Object> details = allCoupon.getDetails();
                        Object productId1 = details.get("product_id");
                        int prod_id=(Integer) productId1;
                        Object discount1 = details.get("discount");
                        int discount2=(Integer) discount1;
                        if (prod_id==productId){
                         product_flag=true;
                         productMap.put(prod_id,discount2);
                        }
                    }
                }

            }

            double sum = items.stream()
                    .mapToDouble(item1 -> item1.getQuantity() * item1.getPrice())
                    .sum();
            System.out.println("Sum : "+sum);
            if (sum >= threshold) {
//                -----------------------------------
                CouponDetails couponDetails = new CouponDetails();
                couponDetails.setCouponId(1);
                couponDetails.setType(couponType);
                couponDetails.setDiscount(sum * (discount/100.0));
                applicableCouponList.add(couponDetails);
                //to give exact json structure
                // Wrap the list of coupons in a Map
            }
          if (product_flag){
              int size = productMap.size();

              for (Map.Entry<Integer, Integer> entry : productMap.entrySet()){
                  Integer key = entry.getKey();
                  Integer value = entry.getValue();

                  System.out.println("Size : "+size);
                  CouponDetails couponDetails2 = new CouponDetails();//
                  couponDetails2.setCouponId(key);
                  couponDetails2.setType("Product-wise");
                  couponDetails2.setDiscount(sum * value/100);
                  applicableCouponList.add(couponDetails2);

              }

          }
            response.put("applicable_coupons", applicableCouponList);
        }
        else {
            System.out.println("Item list is null");
        }
        return response;
    }

    @Override
    public UpdatedCartWrapper getCartResponse(ItemWrapper item, int id) {
            int total_price=0;
            int discounted_amount=0;
        UpdatedCartWrapper updatedCartWrapper=new UpdatedCartWrapper();
        List<CartItem> cartItems=new ArrayList<>();

         Item item1 = item.getItem();
        List<Product> items = item1.getItems();
        for (Product product : items) {

            int productId = product.getProduct_id();
            int quantity = product.getQuantity();
            double price = product.getPrice();
            total_price+= (int) (quantity*price);
            CartItem cartItem1=new CartItem();
            cartItem1.setProduct_id(productId);
            cartItem1.setPrice((int) price);
            cartItem1.setQuantity(quantity);
            cartItem1.setTotal_discount(100);

            cartItems.add(cartItem1);

        }
           if (total_price>100){
               discounted_amount = (int)  (total_price * 0.10);
           }
        int final_price=total_price-discounted_amount;

        CartResponse cartResponse=new CartResponse();
        cartResponse.setItems(cartItems);
        cartResponse.setTotal_Price(total_price);
        cartResponse.setTotal_Discount(discounted_amount);
        cartResponse.setFinal_Price(final_price);
          updatedCartWrapper.setUpdated_Cart(cartResponse);
        return updatedCartWrapper;
    }
}
