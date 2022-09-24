package com.marcus.silva.dev.libraryapi.controller;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody BookSaveForm bookSaveForm){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }
}
