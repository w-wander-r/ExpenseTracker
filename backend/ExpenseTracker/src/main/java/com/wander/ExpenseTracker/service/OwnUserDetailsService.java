package com.wander.ExpenseTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.model.UserPrincipal;
import com.wander.ExpenseTracker.repo.UserRepo;

@Service
public class OwnUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = repo.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException("User not found");
        
        return new UserPrincipal(user);
    }
}
