package org.mflix.forecast.exception;

import java.util.NoSuchElementException;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @Autowired
    private ResponseComponent responseComponent;

    @ExceptionHandler
    public ResponseEntity<ResponseView> handlerNoSuchElement(NoSuchElementException e) {
        String message = e.getMessage().toUpperCase();
        return responseComponent.generate(1, message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseView> handlerMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage().toUpperCase();
        return responseComponent.generate(2, message, HttpStatus.BAD_REQUEST);
    }
}