package com.marcus.silva.dev.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookReturnResponse {

    private String email;
    private BookResponse bookResponse;
}
