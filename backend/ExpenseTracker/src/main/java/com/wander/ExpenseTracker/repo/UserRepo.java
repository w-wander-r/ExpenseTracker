package com.wander.ExpenseTracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wander.ExpenseTracker.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
