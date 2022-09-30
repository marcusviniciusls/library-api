package com.marcus.silva.dev.libraryapi.service;

import com.marcus.silva.dev.libraryapi.dto.request.BookSaveForm;
import com.marcus.silva.dev.libraryapi.dto.request.BookUpdateForm;
import com.marcus.silva.dev.libraryapi.dto.response.BookResponse;
import com.marcus.silva.dev.libraryapi.dto.response.BookReturnResponse;
import com.marcus.silva.dev.libraryapi.exception.custom.BookAlreadyRented;
import com.marcus.silva.dev.libraryapi.exception.custom.IsbnAlreadyExisting;
import com.marcus.silva.dev.libraryapi.exception.custom.ResourceNotFoundException;
import com.marcus.silva.dev.libraryapi.factory.BookFactory;
import com.marcus.silva.dev.libraryapi.model.entities.Book;
import com.marcus.silva.dev.libraryapi.model.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired private ModelMapper modelMapper = new ModelMapper();
    @Autowired private BookRepository bookRepository;
    @Autowired private BookFactory bookFactory;

    public BookResponse saveBook(BookSaveForm bookSaveForm){
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookSaveForm.getIsbn());
        if (optionalBook.isPresent()){
            throw new IsbnAlreadyExisting("ISBN ALREADY EXISTING");
        }
        Book book = modelMapper.map(bookSaveForm, Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookResponse.class);
    }

    public BookResponse findByIdBook(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        Book book = optionalBook.get();
        verifyStatus(book);
        return modelMapper.map(optionalBook.get(), BookResponse.class);
    }

    public void deleteById(Long id){
        try {
            bookRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException ex){
            Book book = bookRepository.findById(id).get();
            book.setStatus(false);
            bookRepository.save(book);
        }
    }

    public BookResponse refreshById(Long id, BookUpdateForm bookUpdateForm){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        verifyStatus(optionalBook.get());
        Book book = optionalBook.get();
        book = bookFactory.convertUpdateFormInBook(book, bookUpdateForm);
        bookRepository.save(book);
        return modelMapper.map(book, BookResponse.class);
    }

    public Page<BookResponse> findAllBook(Pageable pageable){
        Page<Book> bookPage = bookRepository.findAll(pageable);
        Page<BookResponse> bookPageResponse = bookPage.map(b -> bookFactory.convertBookInBookResponse(b));
        return bookPageResponse;
    }

    public Book findByIsbn(String isbn){
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        verifyStatus(optionalBook.get());
        return optionalBook.get();
    }

    public boolean verifyRent(Book book){
        if (book.isRent()){
            throw new BookAlreadyRented("BOOK ALREADY RENTED");
        }
        return true;
    }

    public boolean verifyNotRent(Book book){
        if (!book.isRent()){
            throw new BookAlreadyRented("BOOK NOT RENTED");
        }
        return true;
    }

    private boolean verifyStatus(Book book){
        if (!book.isStatus()){
            throw new ResourceNotFoundException("BOOK NOT FOUND");
        }
        return true;
    }

    public List<BookReturnResponse> bookLaterAndLimitThreeDays(){
        List<Book> listBook = bookRepository.findAllBookGiveToBack();
        List<BookReturnResponse> listBookToEmails = new ArrayList<>();
        LocalDateTime dateNowMore10Days = LocalDateTime.now().plusDays(10);
        for (Book book : listBook){
            if (book.getDateReturn() != null) {
                if (book.getDateReturn().isBefore(LocalDateTime.now()) || book.getDateReturn().isBefore(dateNowMore10Days)){
                    BookReturnResponse bookReturnResponse = bookFactory.convertEntityToReturnResponse(book);
                    listBookToEmails.add(bookReturnResponse);
                }
            }
        }
        return listBookToEmails;
    }
}
