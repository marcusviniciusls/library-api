package com.marcus.silva.dev.libraryapi.exception.central;

import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.exception.custom.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> enumInvalid(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        String error = "CONSTRAINT NOT BLANK";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, methodArgumentNotValidException.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(IsbnAlreadyExisting.class)
    public ResponseEntity<StandardError> isbnNotFoundException(IsbnAlreadyExisting isbnAlreadyExisting, HttpServletRequest httpServletRequest) {
        String error = "ISBN ALREADY EXISTING";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, isbnAlreadyExisting.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException resourceNotFoundException, HttpServletRequest httpServletRequest) {
        String error = "OBJECT NOT FOUND EXCEPTION";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, resourceNotFoundException.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(BookAlreadyRented.class)
    public ResponseEntity<StandardError> bookAlreadyRented(BookAlreadyRented bookAlreadyRented, HttpServletRequest httpServletRequest) {
        String error = "BOOK ALREADY RENTED";
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), httpStatus.value(), error, bookAlreadyRented.getMessage(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(httpStatus).body(standardError);
    }
}
