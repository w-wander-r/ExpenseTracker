package com.wander.ExpenseTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wander.ExpenseTracker.model.Expense;
import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.repo.ExpenseRepo;
import com.wander.ExpenseTracker.repo.UserRepo;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepo expenseRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<Expense> getAllExpenses(Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName());
        return expenseRepo.findByUser(user);
    }
    
}
