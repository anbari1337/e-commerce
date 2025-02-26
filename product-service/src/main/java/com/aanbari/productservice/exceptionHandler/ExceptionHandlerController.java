package com.aanbari.productservice.exceptionHandler;

import com.aanbari.productservice.dto.ExceptionResponse;
import com.aanbari.productservice.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProductNotFoundException.class)
    public @ResponseBody ExceptionResponse productNotFoundExceptionHandler(HttpServletRequest request, Exception exception){
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .url(request.getRequestURL().toString())
                .date(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

}
