package com.marcus.silva.dev.libraryapi.repository;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired private BookRepository bookRepository;

    @Test
    @DisplayName("Deve Retornar o livro caso tenha cadastrado")
    public void returnTrueWhenIsbnExisting(){
        Book book = new Book("Meu livro", "Autor", "12345");
        testEntityManager.persist(book);
        String isbn = "12345";
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        Assertions.assertTrue(optionalBook.isPresent());
    }

    @Test
    @DisplayName("Deve Retornar falso quando nao tiver o livro cadastrado")
    public void returnFalseWhenIsbnNotExisting(){
        String isbn = "12345";
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        Assertions.assertFalse(optionalBook.isPresent());
    }
}
