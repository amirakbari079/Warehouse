package com.example.warehouse.manager;

import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NotFoundException;
import com.example.warehouse.dao.BookDao;
import com.example.warehouse.dto.ItBookResponseDto;
import com.example.warehouse.entity.BookEntity;


import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookManager {
    @Autowired
    BookDao bookDao;

    ItBooksProxy itBooksProxy = new ItBooksProxy();

    public void save(BookEntity book) throws IllegalArgumentException, CustomException, NotFoundException {
        ISBNValidator validator = new ISBNValidator();
        if (!validator.isValidISBN13(book.getIsbn13())) {
            throw new IllegalArgumentException("isbn13 is not valid");
        }
        if (!validator.isValidISBN10(book.getIsbn10())) {
            throw new IllegalArgumentException("isbn10 is not valid");
        }
        try {
            if (book.getTitle() == null || book.getPrice() == null) {
                try {
                    ItBookResponseDto booksProxy;
                    booksProxy = itBooksProxy.getBookDataFromApi(book.getIsbn13());
                    book.setTitle(booksProxy.getTitle());
                    book.setPrice(booksProxy.getPrice());
                } catch (Exception e) {
                    throw new NotFoundException("there is no book with this isbn13");
                }
            }
            bookDao.save(book);
        } catch (CustomException e) {
            throw new CustomException("There is another book in Database with this Isbn10/Isbn13");
        }

    }


    public BookEntity loadBook(String isbn13) throws Exception {
        return bookDao.loadBook(isbn13);
    }

    public void deleteBook(String isbn13) throws Exception {
        try {
            bookDao.deleteBook(isbn13);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}

