package com.marcus.silva.dev.libraryapi.factory;

import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponsePage;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookFactory {

    @Autowired private ModelMapper modelMapper;

    public Book convertUpdateFormInBook(Book book, BookUpdateForm bookUpdateForm){
        if (bookUpdateForm.getTitle() != null && !bookUpdateForm.getTitle().equals("")){
            book.setTitle(bookUpdateForm.getTitle());
        }
        if (bookUpdateForm.getAuthor() != null && !bookUpdateForm.getAuthor().equals("")){
            book.setAuthor(bookUpdateForm.getAuthor());
        }
        if (bookUpdateForm.getIsbn() != null && !bookUpdateForm.getIsbn().equals("")){
            book.setIsbn(bookUpdateForm.getIsbn());
        }
        return book;
    }

    public BookResponse convertBookInBookResponse(Book book){
        return modelMapper.map(book, BookResponse.class);
    }

    public BookResponsePage convertBookInBookResponsePage(Book book){
        BookResponsePage bookResponsePage = new BookResponsePage();
        bookResponsePage.setTitle(book.getTitle());
        bookResponsePage.setAuthor(book.getAuthor());
        bookResponsePage.setIsbn(book.getIsbn());
        return bookResponsePage;
    }
}
