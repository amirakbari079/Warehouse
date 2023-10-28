package com.example.warehouse.dao;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
class BookDaoTest {

    @Autowired
    BookDao bookDao;

    //----------------------------------Save method-----------------------------------------
    @Test
    void save() {
        CategoryEntity category1 = new CategoryEntity();
        CategoryEntity category2 = new CategoryEntity();
        category1.setSubject("test");
        category1.setCode("dsfa");
        category2.setSubject("testii");
        category2.setCode("dsfdsfsda");
        BookEntity book = new BookEntity();
        List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
        categories.add(category1);
        categories.add(category2);
        book.setCategories(categories);
        book.setTitle("testBook");
        book.setPrice("$230");
        book.setIsbn10("1313231");
        book.setIsbn13("798794654");
        try {
            bookDao.save(book);
        } catch (Exception e) {
            fail();
        }
    }
    //-------------------------------------------------------------------------------------------
    //\/\/\/\\/\/\/\/\/\/\//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
}