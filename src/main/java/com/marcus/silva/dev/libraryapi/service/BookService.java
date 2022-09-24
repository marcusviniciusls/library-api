package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Book save(Book any);
}
