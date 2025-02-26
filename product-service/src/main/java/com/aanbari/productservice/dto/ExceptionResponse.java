package com.aanbari.productservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionResponse {
    private String message;
    private String url;
    private LocalDateTime date;
}
