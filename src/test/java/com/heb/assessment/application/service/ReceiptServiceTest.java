package com.heb.assessment.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.assessment.application.model.Util.Coupon;
import com.heb.assessment.application.model.request.Cart;
import com.heb.assessment.application.model.response.ReceiptDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {ReceiptService.class, CouponService.class})
class ReceiptServiceTest {
    @Autowired ReceiptService receiptService;
    @Value("classpath:cart.json")
    Resource resourceFile;
    public Cart getAvailableCoupons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream dbAsStream = resourceFile.getInputStream();
        Cart cart = objectMapper.readValue(dbAsStream, Cart.class);
        return cart;
    }

    @Test
    void generateReceipt() throws IOException {
        Cart cart = this.getAvailableCoupons();
        ReceiptDto receiptDto = receiptService.generateReceipt(cart);
        assertNotNull(receiptDto);
        assertEquals(6.64, receiptDto.getGrandTotal());

    }
}