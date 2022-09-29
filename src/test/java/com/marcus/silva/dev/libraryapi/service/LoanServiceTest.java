package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class LoanServiceTest {

    @MockBean private LoanService loanService;

    @Test
    @DisplayName("Salva um emprestimo")
    public void saveLoanSuccess(){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        LoanResponse loanResponse = new LoanResponse(LocalDateTime.now(), "description", "namePerson", bookResponse);
        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willReturn(loanResponse);
        Assertions.assertThat(loanResponse.getDateLoan()).isNotNull();
        Assertions.assertThat(loanResponse.getDescription()).isEqualTo("description");
        Assertions.assertThat(loanResponse.getNamePerson()).isEqualTo("namePerson");
        Assertions.assertThat(loanResponse.getBookResponse()).isNotNull();
    }

    @Test
    @DisplayName("Salva um emprestimo com erro porque nao achou o livro")
    public void saveLoanNotFoundBook(){
        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willThrow(new ResourceNotFoundException(""));
        Assertions.assertThat(ResourceNotFoundException.class).isNotNull();
    }

    @Test
    @DisplayName("Salva um emprestimo com erro porque o livro já está alugado")
    public void saveLoanBookAlreadyRented(){
        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willThrow(new BookAlreadyRented(""));
        Assertions.assertThat(BookAlreadyRented.class).isNotNull();
    }
}
