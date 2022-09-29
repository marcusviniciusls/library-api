package com.marcus.silva.dev.libraryapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoanSaveForm {

    private String description;
    @NotBlank
    private String namePerson;
    @NotEmpty
    private String isbn;

}

