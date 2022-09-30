package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.BookReturnResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("Buscar um livro por Id")
    public void findByIdTest(){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.findByIdBook(Mockito.anyLong())).willReturn(bookResponse);
        Assertions.assertThat(bookResponse.getId()).isNotNull();
        Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("Autor");
        Assertions.assertThat(bookResponse.getTitle()).isEqualTo("Meu livro");
        Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("12345");
    }

    @Test
    @DisplayName("Buscar um livro por Id")
    public void findByIdInFailureTest(){
        BookResponse bookResponse = new BookResponse(1l, "Meu livro", "Autor", "12345");
        BDDMockito.given(bookService.findByIdBook(Mockito.anyLong())).willThrow(new IsbnAlreadyExisting("ISBN ALREADY EXISTING"));
        Assertions.assertThat(bookResponse.getId()).isNotNull();
        Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("Autor");
        Assertions.assertThat(bookResponse.getTitle()).isEqualTo("Meu livro");
        Assertions.assertThat(bookResponse.getIsbn()).isEqualTo("12345");
    }

    @Test
    @DisplayName("Buscar todos os livros")
    public void findAll(){
        PageRequest pageRequest = PageRequest.of(1, 10);
        List<Book> listBook = new ArrayList<>();
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        listBook.add(new Book(1l, "title", "author", "isbn"));
        Page<Book> page = new PageImpl<Book>(listBook, pageRequest, 10);
        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        Assertions.assertThat(page.getSize()).isEqualTo(10);
        Assertions.assertThat(page.get()).isNotEmpty();
        Assertions.assertThat(page.get()).isNotNull();
    }

    @Test
    @DisplayName("Livro Alugado")
    public void verifyRentSuccessTest(){
        boolean verify = true;
        Mockito.when(bookService.verifyRent(Mockito.any(Book.class))).thenReturn(verify);
        Assertions.assertThat(verify).isEqualTo(true);
    }

    @Test
    @DisplayName("Livro Nao Alugado Alugado")
    public void verifyRentErrorTest(){
        Mockito.when(bookService.verifyRent(Mockito.any(Book.class))).thenThrow(new BookAlreadyRented(""));
        boolean verify = false;
        Assertions.assertThat(verify).isEqualTo(false);
    }

    @Test
    @DisplayName("Quantidade de livros para enviar e-mail")
    public void getBookToReturnForEmail(){
        BookReturnResponse book1 = new BookReturnResponse("vinicius@gmail.com", new BookResponse());
        BookReturnResponse book2 = new BookReturnResponse("vinicius@gmail.com", new BookResponse());
        BookReturnResponse book3 = new BookReturnResponse("vinicius@gmail.com", new BookResponse());
        BookReturnResponse book4 = new BookReturnResponse("vinicius@gmail.com", new BookResponse());
        BookReturnResponse book5 = new BookReturnResponse("vinicius@gmail.com", new BookResponse());
        List<BookReturnResponse> listBook = new ArrayList<>();
        listBook.addAll(Arrays.asList(book5, book1, book2, book3, book4));
        Mockito.when(bookService.bookLaterAndLimitTenDays()).thenReturn(listBook);
        Assertions.assertThat(listBook.size()).isEqualTo(5);
    }
}
