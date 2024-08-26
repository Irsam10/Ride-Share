package com.sam.user_service.controller;

import com.sam.user_service.dto.UserRequest;
import com.sam.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return "User created";
    }
}
