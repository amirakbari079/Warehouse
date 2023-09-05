package com.example.warehouse.manager;

import com.example.warehouse.dao.BookDao;
import com.example.warehouse.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookManager {
    @Autowired
    BookDao bookDao;
 public void save(BookEntity book){
     bookDao.save(book);
 }
}
