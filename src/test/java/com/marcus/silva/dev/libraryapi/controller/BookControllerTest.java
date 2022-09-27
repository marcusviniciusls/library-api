package com.marcus.silva.dev.libraryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.service.BookService;
import org.hamcrest.Matchers;
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
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static String URL_BOOK_API = "/api/books";
    @Autowired private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Create a Book Success")
    public void createBookSuccessTest() throws Exception {
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BookSaveForm bookSaveForm = new BookSaveForm("Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.saveBook(Mockito.any(BookSaveForm.class))).willReturn(bookResponse);
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
    public void createBookUnsuccessfullyTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(null);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL_BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Book com o mesmo ISBN")
    public void bookIsbnEquals() throws Exception {
        BookSaveForm bookSaveForm = new BookSaveForm("teste", "teste", "12345");
        String json = new ObjectMapper().writeValueAsString(bookSaveForm);
        BDDMockito.given(bookService.saveBook(Mockito.any(BookSaveForm.class))).willThrow(new IsbnAlreadyExisting("ISBN ALREADY EXISTING"));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(URL_BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
