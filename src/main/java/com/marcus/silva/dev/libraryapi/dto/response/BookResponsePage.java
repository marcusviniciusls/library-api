package com.marcus.silva.dev.libraryapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookResponsePage {

    private String title;
    private String author;
    private String isbn;
    private List<LoanResponse> listLoanResponse = new ArrayList<>();

    public void addListLoanResponse(LoanResponse loanResponse){
        this.listLoanResponse.add(loanResponse);
    }
}
