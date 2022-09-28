package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.factory.BookFactory;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired private ModelMapper modelMapper = new ModelMapper();
    @Autowired private BookRepository bookRepository;
    @Autowired private BookFactory bookFactory;

    public BookResponse saveBook(BookSaveForm bookSaveForm){
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookSaveForm.getIsbn());
        if (optionalBook.isPresent()){
            throw new IsbnAlreadyExisting("ISBN ALREADY EXISTING");
        }
        Book book = modelMapper.map(bookSaveForm, Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookResponse.class);
    }

    public BookResponse findByIdBook(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        return modelMapper.map(optionalBook.get(), BookResponse.class);
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public BookResponse refreshById(Long id, BookUpdateForm bookUpdateForm){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        Book book = optionalBook.get();
        book = bookFactory.convertUpdateFormInBook(book, bookUpdateForm);
        bookRepository.save(book);
        return modelMapper.map(book, BookResponse.class);
    }
}
