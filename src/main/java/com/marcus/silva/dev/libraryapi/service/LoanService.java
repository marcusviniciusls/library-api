package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.LoanReturnSave;
import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;

import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;

import com.marcus.silva.dev.libraryapi.dto.response.BookResponsePage;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.factory.BookFactory;
import com.marcus.silva.dev.libraryapi.factory.LoanFactory;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import com.marcus.silva.dev.libraryapi.model.repository.LoanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired private LoanFactory loanFactory;
    @Autowired private BookRepository bookRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private LoanRepository loanRepository;
    @Autowired private BookService bookService;
    @Autowired private BookFactory bookFactory;


    public LoanResponse saveLoan(LoanSaveForm loanSaveForm){
        Loan loan = loanFactory.convertSaveFormToEntity(loanSaveForm);
        Book book = bookService.findByIsbn(loanSaveForm.getIsbn());
        bookService.verifyRent(book);
        book.setRent(true);
        loan.setBook(book);
        loan = loanRepository.save(loan);

        LoanResponse loanResponse = modelMapper.map(loan, LoanResponse.class);
        BookResponse bookResponse = modelMapper.map(loan.getBook(), BookResponse.class);
        loanResponse.setBookResponse(bookResponse);
        return loanResponse;
    }

    public boolean loanReturn(LoanReturnSave loanReturnSave){
        Book book = bookService.findByIsbn(loanReturnSave.getIsbn());
        bookService.verifyNotRent(book);
        book.setRent(false);
        Loan loan = new Loan("Retorno de Devolucao", loanReturnSave.getNamePerson(), book);
        loanRepository.save(loan);
        return true;
    }

    public LoanResponse findById(Long id){
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isEmpty()){
            throw new ResourceNotFoundException("LOAN NOT FOUND");
        }
        LoanResponse loanResponse = modelMapper.map(optionalLoan.get(), LoanResponse.class);
        BookResponse bookResponse = modelMapper.map(optionalLoan.get().getBook(), BookResponse.class);
        loanResponse.setBookResponse(bookResponse);
        return loanResponse;
    }

    public BookResponsePage findAllLoanResponse(Long idBook){
        List<Loan> listLoan = loanRepository.findAllLoanByOneBook(idBook);
        Optional<Book> optionalBook = bookRepository.findById(idBook);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        BookResponsePage bookResponsePage = bookFactory.convertBookInBookResponsePage(optionalBook.get());
        for (Loan loan : listLoan){
            LoanResponse loanResponse = loanFactory.convertEntityInResponse(loan);
            bookResponsePage.addListLoanResponse(loanResponse);
        }
        return bookResponsePage;
    }
}
