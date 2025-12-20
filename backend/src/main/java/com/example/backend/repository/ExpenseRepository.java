package com.example.backend.repository;

import com.example.backend.entity.Expense;
import com.example.backend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
   List<Expense> findByGroup(Group group);
}
