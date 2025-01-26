package com.z7.userservice.service;

import com.z7.userservice.dto.LoginDto;
import com.z7.userservice.dto.SignupDto;
import com.z7.userservice.entity.User;
import com.z7.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public User signup(@NonNull SignupDto user) {
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public User login(LoginDto loginDto) {

        return userRepository.findByEmail(loginDto.getEmail()).orElseThrow(); // TODO UserNotFound Exception
    }

}
