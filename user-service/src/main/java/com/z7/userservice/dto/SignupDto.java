package com.z7.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDto {

    @Size(min = 5, max = 50, message = "Full name must be between 5 and 50 characters")
    @NotBlank(message = "Full name is required")
    private String  fullName;

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


}
