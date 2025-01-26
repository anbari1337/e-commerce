package com.z7.userservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ExceptionInfo {
    private String url;
    private String message;
    private LocalDateTime date;
}
