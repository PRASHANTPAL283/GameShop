package com.example.SchoolMangementSystem.ErrorHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseException {

    @ExceptionHandler(GlobalExceptionClass.class)
    public ResponseEntity<ErrorDTO> getGlobalException(GlobalExceptionClass exceptionClass){
        ErrorDTO errorDTO=new ErrorDTO();
        errorDTO.setMessage(exceptionClass.getErrorMessage());
        errorDTO.setStatus(exceptionClass.getHttpStatus());
        return ResponseEntity.status(exceptionClass.getHttpStatus())
                .body(errorDTO);
    }
}
