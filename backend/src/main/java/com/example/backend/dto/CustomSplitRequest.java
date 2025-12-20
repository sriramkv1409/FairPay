package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CustomSplitRequest {
    @NotBlank
    private String description;

    private Map<Long,Double> userAmounts;
}
