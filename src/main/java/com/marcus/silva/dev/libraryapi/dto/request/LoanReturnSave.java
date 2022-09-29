package com.marcus.silva.dev.libraryapi.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LoanReturnSave {

    @NotBlank
    private String isbn;
    @NotBlank
    private String namePerson;
}
