package com.marcus.silva.dev.libraryapi.model.repository;

import com.marcus.silva.dev.libraryapi.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
