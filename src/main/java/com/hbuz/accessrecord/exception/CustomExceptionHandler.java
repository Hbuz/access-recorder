package com.hbuz.accessrecord.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public final ResponseEntity<Object> handleFormatExceptions(DateTimeParseException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getParsedString() + " is not valid");
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid parameter", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerExceptions(NullPointerException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.toString());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Parameter should not be null", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessRecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(AccessRecordNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, "Access record not found", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}