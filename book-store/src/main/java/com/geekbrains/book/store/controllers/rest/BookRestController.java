package com.geekbrains.book.store.controllers.rest;

import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.entities.dto.BookDto;
import com.geekbrains.book.store.exceptions.BookServiceError;
import com.geekbrains.book.store.exceptions.ResourceNotFoundException;
import com.geekbrains.book.store.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookRestController {
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/dtos")
    public List<BookDto> getAllBooksDto() {
        return bookService.findAllDtos();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createNewBook(@RequestBody Book book) {
        if (book.getId() != null) {
            book.setId(null);
        }
        return bookService.saveOrUpdate(book);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Book modifyBook(@RequestBody Book book) {
        if (!bookService.existsById(book.getId())) {
            throw new ResourceNotFoundException("Book with id: " + book.getId() + " doesn't exists");
        }
        return bookService.saveOrUpdate(book);
    }

    @DeleteMapping
    public void deleteAllBooks() {
        bookService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRNFException(ResourceNotFoundException e) {
        BookServiceError err = new BookServiceError();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setMessage(e.getMessage());
        err.setTimestamp(new Date());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
