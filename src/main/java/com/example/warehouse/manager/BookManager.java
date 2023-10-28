package com.example.warehouse.manager;
import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NotFoundException;
import com.example.warehouse.dao.BookDao;
import com.example.warehouse.dto.BookDtoPage;
import com.example.warehouse.dto.ItBookResponseDto;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import com.example.warehouse.mapper.BookMapper;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookManager {
    @Autowired
    BookDao bookDao;
    @Autowired
    CategoryManager categoryManager;
    ItBooksProxy itBooksProxy = new ItBooksProxy();


    @Transactional
    public void save(BookEntity book) throws IllegalArgumentException, CustomException, NotFoundException {
        isbnValidator(book);
            if (book.getTitle() == null || book.getPrice() == null) {
                try {
                    book=fillNullFields(book);
                } catch (NotFoundException e) {
                    throw new NotFoundException(e.getMessage());
                }
            }
            book=setBookCategories(book);
            bookDao.save(book);
    }

    public void isbnValidator(BookEntity book){
        ISBNValidator validator = new ISBNValidator();
        if (!validator.isValidISBN13(book.getIsbn13())) {
            throw new IllegalArgumentException("isbn13 is not valid");
        }
        if (!validator.isValidISBN10(book.getIsbn10())) {
            throw new IllegalArgumentException("isbn10 is not valid");
        }
    }

    public BookEntity fillNullFields(BookEntity book) throws NotFoundException {
        try {
            ItBookResponseDto booksProxy;
            booksProxy = itBooksProxy.getBookDataFromApi(book.getIsbn13());
            book.setTitle(booksProxy.getTitle());
            book.setPrice(booksProxy.getPrice());
            return book;
        } catch (Exception e) {
            throw new NotFoundException("there is no book with this isbn13");
        }
    }

    public BookEntity setBookCategories(BookEntity book){
        ArrayList<CategoryEntity> newCategory = new ArrayList<>();
        book.getCategories().forEach(n -> {
            try {
                newCategory.add(categoryManager.loadBySubject(n.getSubject()));
            } catch (CategoryNotFoundException e) {
                newCategory.add(categoryManager.createCategory(n.getSubject()));
            }
        });
        book.setCategories(newCategory);
        return book;
    }

    public BookEntity loadBook(String isbn13) throws Exception {
        try {
            return bookDao.loadBook(isbn13);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    public void deleteBook(String isbn13) throws Exception {
        try {
            bookDao.deleteBook(isbn13);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Autowired
    BookMapper bookMapper;

    @Transactional
    public BookDtoPage searchBook(String title, String price, String categoryCode) throws NotFoundException {
        List<BookEntity> books = bookDao.searchBook(title, price, categoryCode);
        if (books.size() == 0) {
            throw new NotFoundException("There is no book with this properties");
        }
        return bookMapper.toDtoPage(books);
    }

    @Transactional
    public void updateBook(String isbn13, String isbn10, String title, String price) throws Exception {
        try {
            bookDao.updateBook(isbn13, isbn10, title, price);
        } catch (Exception e) {
            throw new Exception();
        }
    }
}

