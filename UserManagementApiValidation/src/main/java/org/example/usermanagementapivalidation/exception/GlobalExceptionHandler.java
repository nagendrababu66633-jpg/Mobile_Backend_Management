package org.example.usermanagementapivalidation.exception;

import org.example.usermanagementapivalidation.dto.ApiResponse;
import org.example.usermanagementapivalidation.model.User;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=UserNotFound.class)
    public ResponseEntity<ApiResponse<User>> userNotFound(UserNotFound ex){
        ApiResponse<User> response=new ApiResponse<>(false, ex.getMessage(),"USER_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }@ExceptionHandler(value=DuplicateUserException.class)
    public ResponseEntity<ApiResponse<User>>handleDuplicateUser (DuplicateUserException ex) {
        ApiResponse<User> response = new ApiResponse<>(false, ex.getMessage(), "DUPLICATE_USER");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value=InvalidUserException.class)
    public ResponseEntity<ApiResponse<User>> handleInvalidUserException(InvalidUserException ex) {
        ApiResponse<User> response = new ApiResponse<>(false, ex.getMessage(), "USER_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    Map<String,String> error=new HashMap<>();
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgument(MethodArgumentNotValidException e){
        e.getBindingResult().getAllErrors().forEach(err->error.put(err.getCode(),err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    }
