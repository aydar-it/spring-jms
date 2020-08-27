package com.geekbrains.book.store.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class BookServiceError {
    private int status;
    private String message;
    private Date timestamp;
}
