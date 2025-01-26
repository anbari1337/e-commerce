package com.z7.userservice.service;

import com.z7.userservice.dto.UserDto;
import com.z7.userservice.entity.User;
import com.z7.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> getUsersList() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> UserDto
                        .builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .createdAt(user.getCreatedAt())
                        .build()
                ).toList();
    }
}
