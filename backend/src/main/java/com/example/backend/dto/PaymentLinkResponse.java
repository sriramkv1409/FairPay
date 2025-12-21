package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentLinkResponse {
    private String gpay;
    private String phonepe;
    private String paytm;
}
