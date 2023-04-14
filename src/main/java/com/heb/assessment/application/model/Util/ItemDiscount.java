package com.heb.assessment.application.model.Util;

import com.heb.assessment.application.model.request.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class ItemDiscount {
    Item item;
    List<Coupon> coupons;
    Double itemTotalDiscount;
}
