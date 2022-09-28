package com.marcus.silva.dev.libraryapi.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BookUpdateForm {

    private String title;
    private String author;
    private String isbn;

    public BookUpdateForm(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
