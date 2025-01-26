package com.z7.userservice.dto;

import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionInfo {
    private String url;
    private String message;
    private LocalDateTime date;
}
