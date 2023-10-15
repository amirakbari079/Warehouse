package com.example.warehouse.mapper;


import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.BookDto;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.manager.BookManager;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BookMapper {
    @Autowired
    BookManager bookmanager;

    public BookEntity mapper(BookDto bookDto) throws NullFieldException, CustomException {
        BookEntity bookEntity = new BookEntity();
        if (!StringUtil.isNullOrEmpty(bookDto.getIsbn10())) {
            if (bookDto.getIsbn10().length() == 10)
                bookEntity.setIsbn10(bookDto.getIsbn10());
            else throw new CustomException("Isbn10 must be 10 character");
        } else throw new NullFieldException("Book Isbn10");
        if (!StringUtil.isNullOrEmpty(bookDto.getIsbn13())) {
            if (bookDto.getIsbn13().length() == 13)
                bookEntity.setIsbn13(bookDto.getIsbn13());
            else throw new CustomException("Isbn13 must be 13 character");
        } else throw new NullFieldException("Book Isbn13");
        if (!StringUtil.isNullOrEmpty(bookDto.getTitle())) {
            bookEntity.setTitle(bookDto.getTitle());
        } else throw new NullFieldException("Book Title");
        if (!StringUtil.isNullOrEmpty(bookDto.getPrice())) {
            try {
                Integer num = Integer.parseInt(bookDto.getPrice().substring(1, bookDto.getPrice().length()));
                if (num < 0 || num == 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Number: " + bookDto.getPrice());
            }
            bookEntity.setPrice(bookDto.getPrice());
        } else throw new NullFieldException("Book Price");
        return bookEntity;

    }

    public BookDto toDto(BookEntity bookEntity){
        BookDto bookDto=new BookDto();
        bookDto.setIsbn10(bookEntity.getIsbn10());
        bookDto.setIsbn13(bookEntity.getIsbn13());
        bookDto.setTitle(bookEntity.getTitle());
        bookDto.setPrice(bookEntity.getPrice());
        return bookDto;
    }

}
