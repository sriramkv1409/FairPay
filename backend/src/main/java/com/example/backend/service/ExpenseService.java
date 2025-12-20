package com.example.backend.service;


import com.example.backend.dto.CustomSplitRequest;
import com.example.backend.dto.EqualSplitRequest;
import com.example.backend.dto.GroupSummaryResponse;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    private final UserRepository userRepository;
    public void equalSplit(Long groupId, EqualSplitRequest request){
         Group group = groupRepository.findById(groupId)
                 .orElseThrow(()->new RuntimeException("Group not found"));

         List<GroupMember> members = groupMemberRepository.findByGroup(group);
         int size = members.size();

         double perHead = request.getTotalAmount() / size;

         Expense expense = Expense.builder()
                 .group(group)
                 .description(request.getDescription())
                 .amount(request.getTotalAmount())
                 .build();

        expenseRepository.save(expense);

        for(GroupMember member:members){
            ExpenseShare share = ExpenseShare.builder()
                    .expense(expense)
                    .user(member.getUser())
                    .shareAmount(perHead)
                    .build();
            expenseShareRepository.save(share);
        }
    }

    public void customSplit(Long groupId, CustomSplitRequest request){

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));

        double total= request.getUserAmounts()
                .values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        Expense expense = Expense.builder()
                .group(group)
                .description(request.getDescription())
                .amount(total)
                .build();

        expenseRepository.save(expense);

        request.getUserAmounts().forEach((userId, amount) -> {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ExpenseShare share = ExpenseShare.builder()
                    .expense(expense)
                    .user(user)
                    .shareAmount(amount)
                    .build();

            expenseShareRepository.save(share);
        });

    }

    public List<GroupSummaryResponse> getGroupSummary(Long groupId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<Expense> expenses = expenseRepository.findByGroup(group);

        Map<Long, Double> balanceMap = new HashMap<>();

        for (Expense expense : expenses) {
            List<ExpenseShare> shares = expenseShareRepository.findByExpense(expense);

            for (ExpenseShare share : shares) {
                balanceMap.put(
                        share.getUser().getId(),
                        balanceMap.getOrDefault(share.getUser().getId(), 0.0) + share.getShareAmount()
                );
            }
        }

        List<GroupSummaryResponse> response = new ArrayList<>();

        balanceMap.forEach((userId, amount) -> {
            User user = userRepository.findById(userId).get();
            response.add(new GroupSummaryResponse(userId, user.getName(), amount));
        });

        return response;
    }
}
