package com.marcus.silva.dev.libraryapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
}
