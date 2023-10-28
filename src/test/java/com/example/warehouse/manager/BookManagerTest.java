package com.example.warehouse.manager;
import com.example.warehouse.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
class BookManagerTest {

    @Autowired
    BookManager bookManager;

    //----------------------------------loadBook method-----------------------------------------
    @Test
    void loadBook_ExistedIsbn13_loadSuccessfully() throws Exception {
        assertInstanceOf(BookEntity.class, bookManager.loadBook("6871491954249"));
    }

    @Test
    void loadBook_notExistedIsbn13_exception(){
        assertThrows(Exception.class, () -> bookManager.loadBook("132"));
    }
    //-------------------------------------------------------------------------------------------
    //\/\/\/\\/\/\/\/\/\/\//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
    //----------------------------------deleteBook method----------------------------------------
    @Test
    @Transactional
    void deleteBook_ExistedIsbn13_bookSuccessfullyDelete() throws Exception {
        BookManager bookMg = Mockito.mock(BookManager.class);
        bookMg.deleteBook("2345491954249");
        Mockito.verify(bookMg).deleteBook("2345491954249");
    }

    @Test
    @Transactional
    void deleteBook_notExistedIsbn13_exception(){
        assertThrows(Exception.class, () -> bookManager.deleteBook("132"));
    }
    //----------------------------------------------------------------------------------------------
    //\/\/\/\\/\/\/\/\/\/\//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
    //----------------------------------deleteBook method----------------------------------------
}