package com.z7.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hello-world")
    public String helloWorld(){

        return "Hello World";
    }

}
