package com.marcus.silva.dev.libraryapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Setter
@Getter
public class LoanSaveForm {

    private String description;
    @NotBlank(message = "Name Person Not Found")
    private String namePerson;
    @NotBlank(message = "Email Person Not Found")
    @Email
    private String emailPerson;
    @NotEmpty(message = "ISBN Not Found")
    private String isbn;

    public LoanSaveForm(String description, String namePerson, String isbn) {
        this.description = description;
        this.namePerson = namePerson;
        this.isbn = isbn;
    }

    public LoanSaveForm(String description, String namePerson, String emailPerson, String isbn) {
        this.description = description;
        this.namePerson = namePerson;
        this.emailPerson = emailPerson;
        this.isbn = isbn;
    }
}

