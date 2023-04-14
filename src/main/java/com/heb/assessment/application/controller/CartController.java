package com.heb.assessment.application.controller;

import com.heb.assessment.application.model.request.Cart;
import com.heb.assessment.application.model.response.ReceiptDto;
import com.heb.assessment.application.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    ReceiptService receiptService;
    @PostMapping("getReceipt")
    public ResponseEntity<ReceiptDto> getReceipt(@RequestBody Cart cart){
        ReceiptDto receiptDto = receiptService.generateReceipt(cart);
        return ResponseEntity.ok(receiptDto);
    }
}
