package com.heb.assessment.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.assessment.application.model.response.ReceiptDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartControllerTest {
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getReceipt_ShouldReturn_Receipt() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/cart/getReceipt")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{" +
                        "  \"items\": [" +
                        "    {\"itemName\": \"H-E-B Two Bite Brownies\", \"sku\": 85294241, \"isTaxable\": true, \"ownBrand\": true, \"price\": 3.61 }," +
                        "    {  \"itemName\": \"Halo Top Vanilla Bean Ice Cream\",  \"sku\": 95422042, \"isTaxable\": true, \"ownBrand\": false, \"price\": 3.31 }" +
                        "  ]" +
                        "}");
        MvcResult result = mockMvc.perform(request).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();

        ReceiptDto receiptDto = objectMapper.readValue(result.getResponse().getContentAsString(), ReceiptDto.class);

        assertEquals(6.64, receiptDto.getGrandTotal());
    }
}