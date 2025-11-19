package com.nsp.socialmediaapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private int StatusCode;
    private String error;
    private String path;
    private LocalDateTime timestamp;
    private List<FieldError> errors;
}
