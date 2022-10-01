package com.marcus.silva.dev.libraryapi.controller;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.BookReturnResponse;
import com.marcus.silva.dev.libraryapi.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Api("Book API")
public class BookController {

    @Autowired private BookService bookService;

    @PostMapping
    @ApiOperation("Criar um livro")
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookSaveForm bookSaveForm){
        BookResponse bookResponse = bookService.saveBook(bookSaveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Apagar um livro por Id")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("Deletar um livro por Id")
    public ResponseEntity<BookResponse> deleteById(@PathVariable Long id, @RequestBody BookUpdateForm bookUpdateForm){
        BookResponse bookResponse = bookService.refreshById(id, bookUpdateForm);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Buscar um livro por Id")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id){
        BookResponse bookResponse = bookService.findByIdBook(id);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping
    @ApiOperation("Buscar todos os livros")
    public ResponseEntity<Page<BookResponse>> findAll(Pageable pageable){
        Page<BookResponse> pageBookResponse = bookService.findAllBook(pageable);
        return ResponseEntity.ok(pageBookResponse);
    }

    @GetMapping(value = "/return")
    @ApiOperation("Retornar os livros com prazos de entrega atrasados ou que vai vencer em 10 dias")
    public ResponseEntity<List<BookReturnResponse>> returnBook(){
        List<BookReturnResponse> bookReturnResponses = bookService.bookLaterAndLimitTenDays();
        return ResponseEntity.ok(bookReturnResponses);
    }
}
