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
        Book book = new Book("O menino do pijama lsitrado", "Marcus Vinicius", "12345");

        bookRepository.save(book);

        Loan loan = new Loan(1l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan1 = new Loan(2l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan2 = new Loan(3l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan3 = new Loan(4l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan4 = new Loan(5l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan5 = new Loan(6l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan6 = new Loan(7l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan7 = new Loan(8l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan8 = new Loan(9l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        Loan loan9 = new Loan(10l, LocalDateTime.now(), "Aluguel de livro", "Vitoria", book);
        loanRepository.saveAll(Arrays.asList(loan, loan1, loan2, loan3, loan4, loan5, loan6, loan7, loan8, loan9));
    }
}
