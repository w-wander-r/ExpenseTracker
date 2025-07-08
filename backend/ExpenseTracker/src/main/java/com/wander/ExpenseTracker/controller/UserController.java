package com.wander.ExpenseTracker.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.model.UserDTO;
import com.wander.ExpenseTracker.service.UserService;

import jakarta.validation.Valid;

/**
 * The UserController class provides endpoints for user authentication operations
 * including registration and login. This controller works with UserDTO objects to ensure
 * only necessary information is transferred between client and server.
 * <p>
 * All endpoints are prefixed with "/api/auth" and return appropriate responses for
 * authentication-related operations. The controller delegates business logic to
 * the UserService class.
 * 
 * @see UserService
 * @see UserDTO
 * @see User
 */
@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
            
            return ResponseEntity.badRequest().body(errors);
        }

        if (service.usernameExists(userDTO.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body("Username already exist");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        User savedUser = service.register(user);

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setUsername(savedUser.getUsername());
        savedUserDTO.setPassword(savedUser.getPassword());
        savedUserDTO.setRole(savedUser.getRole());
        
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
            
            return ResponseEntity.badRequest().body(errors);
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        
        try {
            Map<String, String> tokenResponse = service.verify(user);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
