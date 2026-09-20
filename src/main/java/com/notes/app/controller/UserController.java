package com.notes.app.controller;

import com.notes.app.dto.LoginRequest;
import com.notes.app.dto.LoginResponse;
import com.notes.app.dto.UserRequest;
import com.notes.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserRequest request) {
        userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        String token = userService.login(request);
        return new LoginResponse(token);
    }
}
