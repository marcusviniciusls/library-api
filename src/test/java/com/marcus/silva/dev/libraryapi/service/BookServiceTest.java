package com.marcus.silva.dev.libraryapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class BookServiceTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Salva um Livro")
    public void saveBookTest(){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.saveBook(Mockito.any(BookSaveForm.class))).willReturn(bookResponse);
        Assertions.assertThat(bookResponse.getId()).isNotNull();
        Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("Autor");
        Assertions.assertThat(bookResponse.getTitle()).isEqualTo("Meu livro");
        Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("12345");
    }
}
