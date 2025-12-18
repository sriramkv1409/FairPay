package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CreateGroupRequest {

    @NotBlank
    private String groupName;

    @NotBlank
    private String upiId;

}
