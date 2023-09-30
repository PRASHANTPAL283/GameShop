package com.example.SchoolMangementSystem.ErrorHandler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class GlobalExceptionClass extends RuntimeException{
    public String errorMessage;
    public HttpStatus httpStatus;

    public GlobalExceptionClass(String errorMessage, HttpStatus status){
        this.errorMessage=errorMessage;
        this.httpStatus=status;
    }
}
