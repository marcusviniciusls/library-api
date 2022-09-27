package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.config.ModelMapperConfiguration;
import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired private ModelMapper modelMapper = new ModelMapper();
    @Autowired private BookRepository bookRepository;

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
}
