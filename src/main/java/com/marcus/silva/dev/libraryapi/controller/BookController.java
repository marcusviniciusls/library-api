package com.marcus.silva.dev.libraryapi.controller;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookSaveForm bookSaveForm){
        BookResponse bookResponse = bookService.saveBook(bookSaveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id){
        BookResponse bookResponse = bookService.findByIdBook(id);
        return ResponseEntity.ok(bookResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> deleteById(@PathVariable Long id, @RequestBody BookUpdateForm bookUpdateForm){
        BookResponse bookResponse = bookService.refreshById(id, bookUpdateForm);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> findAll(Pageable pageable){
        Page<BookResponse> pageBookResponse = bookService.findAllBook(pageable);
        return ResponseEntity.ok(pageBookResponse);
    }
}
