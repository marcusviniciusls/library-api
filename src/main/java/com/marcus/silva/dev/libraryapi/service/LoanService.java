package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.factory.LoanFactory;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import com.marcus.silva.dev.libraryapi.model.repository.LoanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {

    @Autowired private LoanFactory loanFactory;
    @Autowired private BookRepository bookRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private LoanRepository loanRepository;

    public LoanResponse saveLoan(LoanSaveForm loanSaveForm){
        Loan loan = loanFactory.convertSaveFormToEntity(loanSaveForm);
        Optional<Book> optionalBook = bookRepository.findByIsbn(loanSaveForm.getIsbn());
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        Book book = optionalBook.get();
        if (book.isRent()){
            throw new BookAlreadyRented("BOOK ALREADY RENTED");
        }
        book.setRent(true);
        loan.setBook(book);
        loan = loanRepository.save(loan);
        LoanResponse loanResponse = modelMapper.map(loan, LoanResponse.class);
        BookResponse bookResponse = modelMapper.map(loan.getBook(), BookResponse.class);
        loanResponse.setBookResponse(bookResponse);
        return loanResponse;
    }
}
