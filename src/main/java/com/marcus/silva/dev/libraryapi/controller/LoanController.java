package com.marcus.silva.dev.libraryapi.controller;


import com.marcus.silva.dev.libraryapi.dto.request.LoanReturnSave;
import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;

import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/loan")
public class LoanController {


    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> create(@Valid @RequestBody LoanSaveForm loanSaveForm){
        LoanResponse loanResponse = loanService.saveLoan(loanSaveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @PostMapping(value = "/return")
    public ResponseEntity<?> returnLoan(@Valid @RequestBody LoanReturnSave loanReturnSave){
        loanService.loanReturn(loanReturnSave);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
