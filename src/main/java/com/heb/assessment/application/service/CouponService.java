package com.heb.assessment.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.assessment.application.model.Util.Coupon;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CouponService {
    @Value("classpath:coupons.json")
    Resource resourceFile;
    public List<Coupon> getAvailableCoupons() throws IOException {
        CouponsFileModel couponsFileModel;
        ObjectMapper objectMapper = new ObjectMapper();
            InputStream dbAsStream = resourceFile.getInputStream();
            couponsFileModel = objectMapper.readValue(dbAsStream,CouponsFileModel.class);
            return couponsFileModel.getCoupons();

    }
    @Data
    static class CouponsFileModel{
        List<Coupon> coupons;
    }
}
