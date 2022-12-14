package com.marcus.silva.dev.libraryapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status = true;
    private LocalDateTime dateLoan = LocalDateTime.now();
    private String description;
    private String namePerson;
    private String emailPerson;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan(String description, String namePerson) {
        this.description = description;
        this.namePerson = namePerson;
    }

    public Loan(String description, String namePerson, Book book) {
        this.description = description;
        this.namePerson = namePerson;
        this.book = book;
    }

    public Loan(LocalDateTime dateLoan, String description, String namePerson, String email, Book book) {
        this.dateLoan = dateLoan;
        this.description = description;
        this.namePerson = namePerson;
        this.emailPerson = email;
        this.book = book;
    }
}
