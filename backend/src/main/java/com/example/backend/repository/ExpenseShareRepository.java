package com.example.backend.repository;

import com.example.backend.entity.Expense;
import com.example.backend.entity.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare,Long> {
    List<ExpenseShare> findByExpense(Expense expense);
}
