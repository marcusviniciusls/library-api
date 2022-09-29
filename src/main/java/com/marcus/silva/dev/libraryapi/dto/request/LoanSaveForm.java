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
    @NotBlank(message = "Name Person Not Found")
    private String namePerson;
    @NotEmpty(message = "ISBN Not Found")
    private String isbn;

}

