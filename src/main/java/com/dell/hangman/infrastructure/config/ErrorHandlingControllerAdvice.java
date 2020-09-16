package com.dell.hangman.infrastructure.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dell.hangman.domain.HangmanDomainException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(HangmanDomainException.class)
    public ResponseEntity<HangmanDomainExceptionError> handleHangmanException(HangmanDomainException ex) {
        return new ResponseEntity<>(new HangmanDomainExceptionError(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
