package com.wander.ExpenseTracker.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wander.ExpenseTracker.model.Expense;
import com.wander.ExpenseTracker.model.User;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long>{
    List<Expense> findByUser(User user);
    Optional<Expense> findByIdAndUser(Long id, User user);
    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
