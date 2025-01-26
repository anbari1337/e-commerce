package com.z7.userservice.exceptionHandler;

import com.z7.userservice.dto.ExceptionInfo;
import com.z7.userservice.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ExceptionInfo internalServerExceptionHandler(HttpServletRequest request, Exception exception) {
        return ExceptionInfo
                .builder()
                .url(String.valueOf(request.getRequestURL()))
                .message("Internal server error")
                .date(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }


    @ExceptionHandler(UserNotFoundException.class)
    public @ResponseBody  ExceptionInfo userNotFoundExceptionHandler(HttpServletRequest request, Exception exception) {
        return ExceptionInfo
                .builder()
                .url(String.valueOf(request.getRequestURL()))
                .message(exception.getMessage())
                .date(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
}
