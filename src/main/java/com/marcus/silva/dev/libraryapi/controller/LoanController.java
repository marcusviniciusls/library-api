package com.marcus.silva.dev.libraryapi.controller;


import com.marcus.silva.dev.libraryapi.dto.request.LoanReturnSave;
import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;

import com.marcus.silva.dev.libraryapi.dto.response.BookResponsePage;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/loan")
@Api("Loan API")
public class LoanController {


    @Autowired
    private LoanService loanService;

    @PostMapping
    @ApiOperation("Criar um emprestimo do livro, caso esteja disponível")
    public ResponseEntity<LoanResponse> create(@Valid @RequestBody LoanSaveForm loanSaveForm){
        LoanResponse loanResponse = loanService.saveLoan(loanSaveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @PostMapping(value = "/return")
    @ApiOperation("Devolver um Livro")
    public ResponseEntity<?> returnLoan(@Valid @RequestBody LoanReturnSave loanReturnSave){
        loanService.loanReturn(loanReturnSave);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Buscar um emprestimo por Id")
    public ResponseEntity<LoanResponse> findById(@PathVariable Long id){
        LoanResponse loanResponse = loanService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(loanResponse);
    }

    @GetMapping(value = "/book/{id}")
    @ApiOperation("Buscar um livro por Id")
    public ResponseEntity<BookResponsePage> findAllLoanPerBook(@PathVariable Long id){
        BookResponsePage bookResponsePage = loanService.findAllLoanResponse(id);
        return ResponseEntity.ok(bookResponsePage);
    }
}
