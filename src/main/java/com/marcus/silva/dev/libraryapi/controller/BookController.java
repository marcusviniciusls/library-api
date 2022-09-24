package com.marcus.silva.dev.libraryapi.controller;

import com.marcus.silva.dev.libraryapi.dto.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    public ResponseEntity<BookResponse> create(){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }
}
