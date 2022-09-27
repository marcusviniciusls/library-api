package com.marcus.silva.dev.libraryapi.config;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@org.springframework.context.annotation.Profile("dev")
public class InitializationData implements CommandLineRunner {

    @Autowired private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book(1l, "O menino do pijama lsitrado", "Marcus Vinicius", "12345");
        bookRepository.save(book);
    }
}
