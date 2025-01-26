package com.z7.userservice.service;

import com.z7.userservice.entity.User;
import com.z7.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
