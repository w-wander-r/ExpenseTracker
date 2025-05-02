package com.wander.ExpenseTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.model.UserDTO;
import com.wander.ExpenseTracker.service.UserService;

// TODO: add validation for register method
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
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        User savedUser = service.register(user);

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setUsername(savedUser.getUsername());
        savedUserDTO.setPassword(savedUser.getPassword());
        savedUserDTO.setRole(savedUser.getRole());
        
        return savedUserDTO;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return service.verify(user);
    }
}
