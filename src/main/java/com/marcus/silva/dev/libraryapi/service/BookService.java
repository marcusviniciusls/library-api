package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.config.ModelMapperConfiguration;
import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired private ModelMapper modelMapper = new ModelMapper();
    @Autowired private BookRepository bookRepository;

    public BookResponse saveBook(BookSaveForm bookSaveForm){
        Book book = modelMapper.map(bookSaveForm, Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookResponse.class);
    }
}
