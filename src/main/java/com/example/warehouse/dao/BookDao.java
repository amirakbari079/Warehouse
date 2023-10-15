package com.example.warehouse.dao;

import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Slf4j
@Component
public class BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(BookEntity book) throws Exception {
        try {
            entityManager.persist(book);
        } catch (Exception e) {
            throw new Exception("There is anothere book in Database with this Isbn10/Isbn13");
        }
        log.info("book is saved to database. {}", book);
    }

    @Transactional
    public BookEntity loadBook(String isbn13) {
        String queryString = "select c from BookEntity c where c.isbn13= :isbn13";
        TypedQuery<BookEntity> typedQuery=entityManager.createQuery(queryString,BookEntity.class);
        typedQuery.setParameter("isbn13",isbn13);
        return typedQuery.getSingleResult();
    }
}
