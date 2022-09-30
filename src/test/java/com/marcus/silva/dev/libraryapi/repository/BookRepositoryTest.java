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

import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    @DisplayName("Trazer livros para devolver")
    public void returnGetBook(){
        Book book = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "1", LocalDateTime.of(2022,  7, 30, 19, 00));
        Book book1 = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "2", LocalDateTime.of(2022, 8, 30, 19, 00));
        Book book2 = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "3", LocalDateTime.of(2022, 9, 30, 19, 00));
        Book book3 = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "4", LocalDateTime.of(2022, 10, 10, 19, 00));
        Book book4 = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "5", LocalDateTime.of(2022, 11, 30, 19, 00));
        book.setRent(true);
        book.setEmailPersonRented("marcus.silva.dev@gmail.com");
        book1.setRent(true);
        book1.setEmailPersonRented("marcus.silva.dev@gmail.com");
        book2.setRent(true);
        book2.setEmailPersonRented("marcus.silva.dev@gmail.com");
        book3.setRent(false);
        book3.setEmailPersonRented("marcus.silva.dev@gmail.com");
        book4.setRent(false);
        book4.setEmailPersonRented("marcus.silva.dev@gmail.com");
        testEntityManager.persist(book);
        testEntityManager.persist(book1);
        testEntityManager.persist(book2);
        testEntityManager.persist(book3);
        testEntityManager.persist(book4);
        List<Book> optionalBook = bookRepository.findAllBookGiveToBack();
        Assertions.assertTrue(optionalBook.size() == 3);
    }
}
