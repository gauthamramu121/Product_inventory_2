package com.example.products.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.products.exceptions.customExceptions.ProductNotFoundException;
import com.example.products.responseDTO.ErrorDTO;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDTO> errorHandler(String message, HttpServletRequest request, HttpStatus status, HashMap<String, String> map) {

        ErrorDTO error = ErrorDTO.builder()
                .message(message)
                .path(request.getRequestURI())
                .status(status.value())
                .timeStamp(LocalDateTime.now())
                .errors(map)
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> inputExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {

        HashMap<String, String> errorMap = new HashMap<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(err -> errorMap.put(err.getField(), err.getDefaultMessage()));

        return errorHandler(exception.getMessage(), request, HttpStatus.BAD_REQUEST, errorMap);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDTO> resourceNotFound(
            NoResourceFoundException exception,
            HttpServletRequest request) {

        return errorHandler(
                "Resource not found",
                request,
                HttpStatus.NOT_FOUND,
                null);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> productNotFound(ProductNotFoundException exception, HttpServletRequest request) {
        return errorHandler(exception.getMessage(), request, HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> invalidArgument(IllegalArgumentException exception, HttpServletRequest request) {
        return errorHandler(exception.getMessage(), request, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> globalException(Exception exception, HttpServletRequest request) {
        log.error("Something went wrong :{}", exception.getMessage());
        return errorHandler(exception.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
