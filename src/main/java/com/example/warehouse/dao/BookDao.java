package com.example.warehouse.dao;

import com.example.warehouse.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Slf4j
@Component
public class BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(BookEntity book) {
        log.info("going to save db :{}", book);
        entityManager.persist(book);
        log.info("book is saved to database. {}", book);
    }
}
