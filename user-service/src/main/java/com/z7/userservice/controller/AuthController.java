package com.z7.userservice.controller;

import com.z7.userservice.dto.LoginDto;
import com.z7.userservice.dto.SignupDto;
import com.z7.userservice.entity.User;
import com.z7.userservice.service.AuthService;
import com.z7.userservice.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto user, BindingResult result) {

        // Check if there are validation errors
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList());
        }

        User newUser = authService.signup(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> signup(@RequestBody LoginDto input) throws Exception{

        User newUser = authService.login(input);
        String token = jwtService.generateToken(newUser);
        return ResponseEntity.ok(token);
    }

}
