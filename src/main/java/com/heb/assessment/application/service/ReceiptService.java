package com.heb.assessment.application.service;

import com.heb.assessment.application.model.Util.Coupon;
import com.heb.assessment.application.model.Util.ItemDiscount;
import com.heb.assessment.application.model.request.Cart;
import com.heb.assessment.application.model.response.ReceiptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {
    @Autowired CouponService couponService;
    public ReceiptDto generateReceipt(Cart cart) {
        try {
            List<Coupon> coupons = couponService.getAvailableCoupons();
            List<ItemDiscount> itemDiscounts =
            cart.getItems().stream().map(item -> {
                List<Coupon> couponListTmp = coupons.stream().filter(c->c.getAppliedSku() == item.getSku()).collect(Collectors.toList());
                Double subDiscountTotal = couponListTmp.stream().map(coupon -> coupon.getDiscountPrice()).reduce(0.0, Double::sum);
                return ItemDiscount.builder().coupons(couponListTmp).item(item).itemTotalDiscount(subDiscountTotal).build();
            }).collect(Collectors.toList());

            Double taxTotal = this.getTaxTotal(itemDiscounts);
            Double taxableSubTotalAfterDiscount = this.getTaxableSubTotalAfterDiscount(itemDiscounts);
            Double subTotalAfterDiscount = this.getSubTotalAfterDiscount(itemDiscounts);
            Double discountTotal = this.getDiscountTotal(itemDiscounts);
            Double subTotalBeforeDiscount = this.getSubTotalBeforeDiscount(cart);
            Double grandTotal = taxTotal + subTotalAfterDiscount;


            return ReceiptDto.builder()
                    .grandTotal(this.round2DecimalPlaces(grandTotal))
                    .subTotalBeforeDiscount(this.round2DecimalPlaces(subTotalBeforeDiscount))
                    .discountTotal(this.round2DecimalPlaces(discountTotal))
                    .taxableSubTotalAfterDiscount(this.round2DecimalPlaces(taxableSubTotalAfterDiscount))
                    .subTotalAfterDiscount(this.round2DecimalPlaces(subTotalAfterDiscount))
                    .taxTotal(this.round2DecimalPlaces(taxTotal))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    Double round2DecimalPlaces(Double amount){
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(amount));
    }

    Double getSubTotalBeforeDiscount(Cart cart){
        return cart.getItems().stream().map(item -> item.getPrice()).reduce(0.0, Double::sum);
    }

    Double getDiscountTotal(List<ItemDiscount> itemDiscounts){
        return itemDiscounts.stream()
                .map(itemDiscount -> itemDiscount.getCoupons().stream()
                        .map(coupon -> coupon.getDiscountPrice()).reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum);
    }

    Double getSubTotalAfterDiscount(List<ItemDiscount> itemDiscounts){
        return itemDiscounts.stream()
                .map(itemDiscount -> {
                    Double priceAfterDiscount = itemDiscount.getItem().getPrice()-itemDiscount.getItemTotalDiscount();
                    return priceAfterDiscount>0.0?priceAfterDiscount:0.0;
                })
                .reduce(0.0, Double::sum);
    }
    Double getTaxableSubTotalAfterDiscount(List<ItemDiscount> itemDiscounts){
        return itemDiscounts.stream()
                .map(itemDiscount -> {
                    Double priceAfterDiscount = itemDiscount.getItem().getPrice()-itemDiscount.getItemTotalDiscount();
                    priceAfterDiscount = priceAfterDiscount>0.0?priceAfterDiscount:0.0;

                    if(itemDiscount.getItem().getIsTaxable()) return priceAfterDiscount;
                    return 0.0;
                })
                .reduce(0.0, Double::sum);
    }
    Double getTaxTotal(List<ItemDiscount> itemDiscounts){
        return itemDiscounts.stream()
                .map(itemDiscount -> {
                    Double priceAfterDiscount = itemDiscount.getItem().getPrice()-itemDiscount.getItemTotalDiscount();
                    priceAfterDiscount = priceAfterDiscount>0.0?priceAfterDiscount:0.0;

                    if(itemDiscount.getItem().getIsTaxable()) return priceAfterDiscount*0.0825;
                    return 0.0;
                })
                .reduce(0.0, Double::sum);
    }
}
