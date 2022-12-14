package com.marcus.silva.dev.libraryapi.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status = true;
    private LocalDateTime dateReturn;
    private String title;
    private String author;
    private String isbn;
    private boolean rent = false;
    private String emailPersonRented;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Loan> listLoan = new ArrayList<>();

    public Book(String title, String author, String isbn, LocalDateTime dateReturn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.dateReturn = dateReturn;
    }

    public Book(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
