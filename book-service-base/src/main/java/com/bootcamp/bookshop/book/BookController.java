package com.bootcamp.bookshop.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    ResponseEntity<List<BookResponse>> list() {
        List<Book> books = bookService.fetchAll();
        List<BookResponse> bookResponse =  books.stream()
                .map(Book::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }
}
