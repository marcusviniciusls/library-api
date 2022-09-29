package com.marcus.silva.dev.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanResponse {

    private LocalDateTime dateLoan;
    private String description;
    private String namePerson;
    private String emailPerson;
    private BookResponse bookResponse;

    public LoanResponse(LocalDateTime dateLoan, String description, String namePerson, BookResponse bookResponse) {
        this.dateLoan = dateLoan;
        this.description = description;
        this.namePerson = namePerson;
        this.bookResponse = bookResponse;
    }
}
