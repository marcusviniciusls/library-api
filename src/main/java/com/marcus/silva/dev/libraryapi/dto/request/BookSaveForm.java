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

    @NotBlank(message = "Title Not Found")
    private String title;
    @NotBlank(message = "Author Not Found")
    private String author;
    @NotBlank(message = "ISBN Not Found")
    private String isbn;
}
