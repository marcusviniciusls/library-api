package com.marcus.silva.dev.libraryapi.config;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import com.marcus.silva.dev.libraryapi.model.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@org.springframework.context.annotation.Profile("dev")
public class InitializationData implements CommandLineRunner {

    @Autowired private BookRepository bookRepository;
    @Autowired private LoanRepository loanRepository;

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book(1l, "O menino do pijama lsitrado", "Marcus Vinicius", "12345");
        bookRepository.save(book);

        Loan loan = new Loan(1l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        loanRepository.save(loan);
    }
}
