package com.marcus.silva.dev.libraryapi.config;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import com.marcus.silva.dev.libraryapi.model.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@org.springframework.context.annotation.Profile("dev")
public class InitializationData implements CommandLineRunner {

    @Autowired private BookRepository bookRepository;
    @Autowired private LoanRepository loanRepository;

    @Override
    public void run(String... args) throws Exception {
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
        book3.setRent(true);
        book3.setEmailPersonRented("marcus.silva.dev@gmail.com");
        book4.setRent(true);
        book4.setEmailPersonRented("marcus.silva.dev@gmail.com");
        bookRepository.saveAll(Arrays.asList(book, book1, book2, book3, book4));

        Loan loan = new Loan( LocalDateTime.now(),
                "Aluguel de livro", "Marcus", "marcus.silva.dev@gmail.com", book);
        Loan loan1 = new Loan( LocalDateTime.now(),
                "Aluguel de livro", "Vinicius", "marcus.silva.dev@gmail.com", book1);
        Loan loan2 = new Loan( LocalDateTime.now(),
                "Aluguel de livro", "Emily", "marcus.silva.dev@gmail.com", book2);
        Loan loan3 = new Loan( LocalDateTime.now(),
                "Aluguel de livro", "Vitoria", "marcus.silva.dev@gmail.com", book3);
        Loan loan4 = new Loan( LocalDateTime.now(),
                "Aluguel de livro", "Silva", "marcus.silva.dev@gmail.com", book4);
        loanRepository.saveAll(Arrays.asList(loan, loan1, loan2, loan3, loan4));
    }
}
