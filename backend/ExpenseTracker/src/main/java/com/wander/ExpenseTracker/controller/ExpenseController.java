package com.wander.ExpenseTracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense, Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName());
        expense.setUser(user);
        return expenseRepo.save(expense);
    }
    
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id, Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName());
        Expense expense = expenseRepo.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepo.delete(expense);
    }

    @GetMapping("/report")
    public Map<String, BigDecimal> getMonthlyReport(
        @RequestParam int year,
        @RequestParam int month,
        Authentication authentication
    ) {
        User user = userRepo.findByUsername(authentication.getName());
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Expense> expenses = expenseRepo.findByUserAndDateBetween(user, start, end);
        BigDecimal total = expenses.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of("totalSpent", total);
    }
}
