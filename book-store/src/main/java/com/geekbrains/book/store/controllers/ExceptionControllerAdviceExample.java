package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.exceptions.BookServiceError;
import com.geekbrains.book.store.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

//@ControllerAdvice
public class ExceptionControllerAdviceExample {
//    @ExceptionHandler
//    public ResponseEntity<?> handleRNFException(ResourceNotFoundException e) {
//        BookServiceError err = new BookServiceError();
//        err.setStatus(HttpStatus.NOT_FOUND.value());
//        err.setMessage(e.getMessage());
//        err.setTimestamp(new Date());
//        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
//    }
}
