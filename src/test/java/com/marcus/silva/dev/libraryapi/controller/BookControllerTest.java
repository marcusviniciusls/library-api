package com.marcus.silva.dev.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.service.BookService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private String URL_BOOK_API = "/api/books";
    @Autowired private MockMvc mockMvc;
    @MockBean private BookService bookService;

    @Test
    @DisplayName("Create a Book Success")
    public void createBookSuccessTest() throws Exception {
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        Book book = new Book(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(book);
        String json = new ObjectMapper().writeValueAsString(bookResponse);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL_BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(bookResponse.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(bookResponse.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(bookResponse.getIsbn()));
    }

    @Test
    @DisplayName("Create a Book Unsuccessfully")
    public void createBookUnsuccessfullyTest(){

    }
}
