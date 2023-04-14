package com.heb.assessment.application.model.Util;

import lombok.Data;

@Data
public class Coupon {
    String couponName;
    long appliedSku;
    Double discountPrice;
}
