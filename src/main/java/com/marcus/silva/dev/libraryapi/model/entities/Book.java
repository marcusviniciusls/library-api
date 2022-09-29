package com.marcus.silva.dev.libraryapi.model.entities;

import lombok.*;

import javax.persistence.*;
import java.awt.desktop.AboutEvent;
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
    private String title;
    private String author;
    private String isbn;
    private boolean rent = false;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Loan> listLoan = new ArrayList<>();

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Book(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
