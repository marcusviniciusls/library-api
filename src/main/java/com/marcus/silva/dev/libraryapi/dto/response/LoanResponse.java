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
    private BookResponse bookResponse;
}
