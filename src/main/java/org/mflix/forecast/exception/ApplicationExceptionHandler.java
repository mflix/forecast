package org.mflix.forecast.exception;

import java.util.NoSuchElementException;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @Autowired
    private ResponseComponent responseComponent;

    @ExceptionHandler({ NoSuchElementException.class, EmptyResultDataAccessException.class,
            MethodArgumentNotValidException.class, HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ResponseView> handlerNoSuchElement(Exception e) {
        if (e instanceof NoSuchElementException || e instanceof EmptyResultDataAccessException) {
            var message = e.getMessage().toLowerCase();
            return responseComponent.generate(StatusEnumeration.F1, HttpStatus.NOT_FOUND, message);
        } else if (e instanceof MethodArgumentNotValidException) {
            var fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
            fieldError.getField();
            fieldError.getDefaultMessage();
            var message = ("[" + fieldError.getField() + "] " + fieldError.getDefaultMessage()).toLowerCase();
            return responseComponent.generate(StatusEnumeration.F2, HttpStatus.BAD_REQUEST, message);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            var message = e.getMessage().toLowerCase();
            return responseComponent.generate(StatusEnumeration.F3, HttpStatus.BAD_REQUEST, message);
        }
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }
}