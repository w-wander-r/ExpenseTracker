package com.wander.ExpenseTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.wander.ExpenseTracker.model.Role.USER;
import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); 

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(USER);
        return repo.save(user);
    }

    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
                );

        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "err";
    }

    public boolean usernameExists(String username) {
        return repo.findByUsername(username).isPresent();
    }
}
