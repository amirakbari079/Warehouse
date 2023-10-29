package com.example.warehouse.service;

import com.example.warehouse.Exception.NotFoundException;
import com.example.warehouse.dto.BookDtoPage;
import com.example.warehouse.manager.BookManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    BookService bookService;
    @Mock
    BookManager bookManager;

    @Test
    void search_successfully_200Response() throws NotFoundException {
        BookDtoPage bookDtoPage=new BookDtoPage(null,1,1, 1L);
        Mockito.when(bookManager.searchBook("title","price","code")).thenReturn(bookDtoPage);
       assertEquals(200,bookService.search("title","price","code").getStatus());

    }
    @Test
    void search_notFound_404Response() throws NotFoundException {
        Mockito.when(bookManager.searchBook("title","price","code")).thenThrow(NotFoundException.class);
        assertEquals(404,bookService.search("title","price","code").getStatus());

    }
}