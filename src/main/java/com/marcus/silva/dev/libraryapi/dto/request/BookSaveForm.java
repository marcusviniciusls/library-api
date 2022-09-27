package com.marcus.silva.dev.libraryapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookSaveForm {

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;
    @NotNull
    @NotBlank
    @NotEmpty
    private String author;
    @NotNull
    @NotBlank
    @NotEmpty
    private String isbn;
}
