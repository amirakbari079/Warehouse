package com.example.warehouse.mapper;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.BookDto;
import com.example.warehouse.dto.BookDtoPage;
import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

    @Autowired
    CategoryMapper categoryMapper;

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
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setCategories(categoryMapper.categoryListDtoToEntity(bookDto.getCategories()));
        return bookEntity;

    }

    public BookDto toDto(BookEntity bookEntity) {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn10(bookEntity.getIsbn10());
        bookDto.setIsbn13(bookEntity.getIsbn13());
        bookDto.setTitle(bookEntity.getTitle());
        bookDto.setPrice(bookEntity.getPrice());
        bookDto.setCategories(categoryMapper.categoryListEntityToDto(bookEntity.getCategories()));
        return bookDto;
    }

    public BookDtoPage toDtoPage(List<BookEntity> bookEntityList) {
        BookDto bookDto = new BookDto();
        BookDtoPage bookDtoPage = new BookDtoPage();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity book : bookEntityList) {
            bookDto.setIsbn10(book.getIsbn10());
            bookDto.setIsbn13(book.getIsbn13());
            bookDto.setTitle(book.getTitle());
            bookDto.setPrice(book.getPrice());
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            for (CategoryEntity category : book.getCategories()) {
                categoryDtoList.add(categoryMapper.toDto(category));
            }
            bookDto.setCategories(categoryDtoList);
            bookDtoList.add(bookDto);
        }
        bookDtoPage.setItems(bookDtoList);
        bookDtoPage.setTotalCount((long) bookDtoList.size());
        return bookDtoPage;
    }

    public List<BookDto> entityToDtoList(List<BookEntity> bookEntities) {
        List<BookDto> books = new ArrayList<>();
        for (BookEntity book : bookEntities) {
            books.add(toDto(book));
        }
        return books;
    }
}
