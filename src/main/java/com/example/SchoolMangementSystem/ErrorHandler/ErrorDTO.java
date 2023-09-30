package com.example.SchoolMangementSystem.ErrorHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorDTO {
    public String message;
    public HttpStatus status;
}
