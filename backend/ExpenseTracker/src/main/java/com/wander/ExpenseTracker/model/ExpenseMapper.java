package com.wander.ExpenseTracker.model;

public class ExpenseMapper {
    
    public static ExpenseDTO toDTO(Expense expense) {

        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        return dto;
    }

    public static Expense toEntity(ExpenseDTO dto) {
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        return expense;
    }
}
