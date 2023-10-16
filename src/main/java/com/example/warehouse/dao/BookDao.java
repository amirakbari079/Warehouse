package com.example.warehouse.dao;

import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

@Slf4j
@Component
public class BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(BookEntity book) throws CustomException {
        entityManager.persist(book);
        log.info("book is saved to database. {}", book);
    }

    @Transactional
    public BookEntity loadBook(String isbn13) throws Exception {
        try {
            String queryString = "select c from BookEntity c where c.isbn13= :isbn13";
            TypedQuery<BookEntity> typedQuery=entityManager.createQuery(queryString,BookEntity.class);
            typedQuery.setParameter("isbn13",isbn13);
            return typedQuery.getSingleResult();
        }catch (Exception e){
            throw new Exception("There is no book with this isbn13");
        }

    }

    @Transactional
    public Response deleteBook(String isbn13) throws Exception {
        try {
            BookEntity book=loadBook(isbn13);
            entityManager.remove(book);
            return Response.ok("Book deleted").build();
        } catch (Exception e) {
            throw new Exception("There is no book with this isbn13");
        }

    }
}
