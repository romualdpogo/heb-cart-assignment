package com.heb.assessment.application.service;

import com.heb.assessment.application.model.Util.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {CouponService.class})
class CouponServiceTest {
    @Autowired
    CouponService couponService;
    @Test
    void getAvailableCoupons() throws IOException {
        List<Coupon> coupons = couponService.getAvailableCoupons();
        assertTrue(coupons.size() == 4);
    }
}