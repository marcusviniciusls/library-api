package com.marcus.silva.dev.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static String URL_BOOK_API = "/api/books";
    private static String URL_BOOK_FIND_BY_ID = "/api/books/";
    private static String URL_BOOK_REFRESH_BY_ID = "/api/books/";
    private static String URL_BOOK_REFRESH_ALL = "/api/books/";
    @Autowired private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private LoanService loanService;

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

    @Test
    @DisplayName("Buscar Livro com Sucesso por Id")
    public void findBookSuccess() throws Exception {
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.findByIdBook(Mockito.anyLong())).willReturn(bookResponse);
        URL_BOOK_FIND_BY_ID = URL_BOOK_FIND_BY_ID + "1";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL_BOOK_FIND_BY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Buscar Livro com sem sucesso por Id")
    public void findBookInsuccess() throws Exception {
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.findByIdBook(Mockito.anyLong())).willThrow(new ResourceNotFoundException(""));
        URL_BOOK_FIND_BY_ID = URL_BOOK_FIND_BY_ID + "2";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL_BOOK_FIND_BY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Error ao atualizar o livro")
    public void deleteBookInsuccess() throws Exception {
        BDDMockito.given(bookService.refreshById(Mockito.anyLong(), Mockito.any(BookUpdateForm.class))).willThrow(new ResourceNotFoundException(""));
        URL_BOOK_REFRESH_BY_ID = URL_BOOK_REFRESH_BY_ID + "2";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(URL_BOOK_REFRESH_BY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Mostrar todos os Livros Teste")
    public void findAllBookSuccess() throws Exception {
        BookResponse bookResponse = new BookResponse();
        BDDMockito.given(bookService.findAllBook(Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<BookResponse>(Arrays.asList(bookResponse)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(URL_BOOK_REFRESH_ALL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
