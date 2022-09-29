package com.marcus.silva.dev.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.service.BookService;
import com.marcus.silva.dev.libraryapi.service.LoanService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    @MockBean private LoanService loanService;
    @MockBean private BookService bookService;
    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("Criar emprestimo de um livro")
    public void createLoanSuccess() throws Exception {
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        LoanResponse loanResponse = new LoanResponse(LocalDateTime.now(), "description", "namePerson", bookResponse);
        LoanSaveForm loanSaveForm = new LoanSaveForm("description", "namePerson", "isbn");

        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willReturn(loanResponse);
        String json = new ObjectMapper().writeValueAsString(loanSaveForm);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(loanResponse.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("namePerson").value(loanResponse.getNamePerson()))
                .andExpect(MockMvcResultMatchers.jsonPath("dateLoan").isNotEmpty());
    }

    @Test
    @DisplayName("Emprestimo incorreto - nao conseguir buscar o livro pelo o ISBN")
    public void createLoanInsuccessBookNotFound() throws Exception {
        LoanSaveForm loanSaveForm = new LoanSaveForm("description", "namePerson", "isbn");

        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willThrow(new ResourceNotFoundException(""));
        String json = new ObjectMapper().writeValueAsString(loanSaveForm);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Teste para tratar do erro do livro já alugado")
    public void createLoadInsuccessBookAlreadyRented() throws Exception {
        LoanSaveForm loanSaveForm = new LoanSaveForm("description", "namePerson", "isbn");

        BDDMockito.given(loanService.saveLoan(Mockito.any(LoanSaveForm.class))).willThrow(new BookAlreadyRented(""));
        String json = new ObjectMapper().writeValueAsString(loanSaveForm);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
