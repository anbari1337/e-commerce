package com.z7.userservice.controller;

import com.z7.userservice.dto.LoginDto;
import com.z7.userservice.dto.SignupDto;
import com.z7.userservice.entity.User;
import com.z7.userservice.service.AuthService;
import com.z7.userservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupDto user) {
        User newUser = authService.signup(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> signup(@RequestBody LoginDto input) {
        User newUser = authService.login(input);

        String token = jwtService.generateToken(newUser);
        return ResponseEntity.ok(token);
    }

}
