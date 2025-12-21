package com.example.backend.controller;

import com.example.backend.dto.PaymentLinkResponse;
import com.example.backend.dto.RandomPayResponse;
import com.example.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{groupId}/random-pay")
    public ResponseEntity<RandomPayResponse> randomPay(@PathVariable Long groupId){
        return ResponseEntity.ok(paymentService.randomPay(groupId));
    }

    @GetMapping("/{groupId}/upi-links")
    public ResponseEntity<PaymentLinkResponse> upiLinks(@PathVariable Long groupId, @RequestParam Double amount){
        return ResponseEntity.ok(paymentService.generateUpiLinks(groupId,amount));
    }

    @PostMapping("/{paymentId}/mark-paid")
    public ResponseEntity<String> markPaid(@PathVariable Long paymentId){
        paymentService.markPaid(paymentId);
        return ResponseEntity.ok("Payment marked as PAID");
    }
}
