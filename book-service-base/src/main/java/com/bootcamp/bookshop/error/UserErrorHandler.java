package com.bootcamp.bookshop.error;

import com.bootcamp.bookshop.user.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler({ InvalidEmailException.class })
    public ResponseEntity<ErrorResponse> handleCreateUserError(Exception ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
