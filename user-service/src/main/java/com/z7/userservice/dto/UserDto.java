package com.z7.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class UserDto {

    private Integer id;
    private String email;
    private String fullName;
    private Date createdAt;
}
