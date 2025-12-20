package com.example.backend.controller;

import com.example.backend.dto.CustomSplitRequest;
import com.example.backend.dto.EqualSplitRequest;
import com.example.backend.dto.GroupSummaryResponse;
import com.example.backend.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/{groupId}/equal-split")
    public ResponseEntity<String> equalSplit(@PathVariable Long groupId, @RequestBody EqualSplitRequest request){
        expenseService.equalSplit(groupId,request);
        return ResponseEntity.ok("Equal split added");
    }

    @PostMapping("/{groupId}/custom-split")
    public ResponseEntity<String> customSplit(@PathVariable Long groupId, @RequestBody CustomSplitRequest request){
         expenseService.customSplit(groupId,request);
         return ResponseEntity.ok("Custom split added");
    }

    @GetMapping("/{groupId}/summary")
    public ResponseEntity<List<GroupSummaryResponse>>summary(@PathVariable Long groupId){
         return ResponseEntity.ok(expenseService.getGroupSummary(groupId));
    }
}
