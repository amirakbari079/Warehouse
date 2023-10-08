package com.example.warehouse.mapper;

import com.example.warehouse.dto.BookDto;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.manager.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    @Autowired
    BookManager bookmanager;

    public Boolean mapper(BookDto bookDto){
        BookEntity bookEntity=new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setIsbn10(bookDto.getIsbn10());
        bookEntity.setIsbn13(bookDto.getIsbn13());
        bookEntity.setPrice(bookDto.getPrice());
        bookmanager.save(bookEntity);
        if (true){
            return true;
        }
        return false;
    }

}
