package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupSummaryResponse {
    private Long userId;
    private String username;
    private Double amountToPay;
}
