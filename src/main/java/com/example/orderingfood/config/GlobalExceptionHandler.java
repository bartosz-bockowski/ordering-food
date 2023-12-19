package com.example.orderingfood.config;

import com.example.orderingfood.exception.NotFoundException;
import com.example.orderingfood.exception.WrongLoginCredentialsException;
import com.example.orderingfood.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongLoginCredentialsException.class)
    public ResponseEntity<ApiError> handleWrongLoginCredentialsException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

}
