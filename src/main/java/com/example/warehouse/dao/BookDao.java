package com.example.warehouse.dao;

import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.entity.BookEntity;
import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
public class BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(BookEntity book) throws CustomException {
        try {
            entityManager.persist(book);
            log.info("book is saved to database. {}", book);
        } catch (Exception e) {
            throw new CustomException();
        }
    }

    @Transactional
    public BookEntity loadBook(String isbn13) throws Exception {
        try {
            String queryString = "select c from BookEntity c where c.isbn13= :isbn13";
            TypedQuery<BookEntity> typedQuery = entityManager.createQuery(queryString, BookEntity.class);
            typedQuery.setParameter("isbn13", isbn13);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            throw new Exception("There is no book with this isbn13");
        }

    }

    @Transactional
    public void deleteBook(String isbn13) throws Exception {
        try {
            BookEntity book = loadBook(isbn13);
            entityManager.remove(book);
        } catch (Exception e) {
            throw new Exception("There is no book with this isbn13");
        }
    }

    public List<BookEntity> searchBook(String title, String price, String categoryCode) {
        CategoryEntity temp = new CategoryEntity();
        temp.setCode("4eefc");
        String conditionQuery = "";
        String queryString = "SELECT e FROM BookEntity e Where 1=1 ";
        if (title != null && !title.isEmpty()) {
            conditionQuery += "AND e.title LIKE :title";
        }
        if (price != null) {
            conditionQuery += "AND e.price= :price";
        }
        if (categoryCode != null) {
            queryString = "SELECT e FROM BookEntity e JOIN e.categories c where c.code = :code ";
        }
        queryString += conditionQuery;
        TypedQuery<BookEntity> query = entityManager.createQuery(queryString, BookEntity.class);
        if (title != null && !title.isEmpty()) {
            query.setParameter("title","%" + title + "%");
        }
        if (price != null && !price.isEmpty()) {
            query.setParameter("price", price);
        }
        if (categoryCode != null && !categoryCode.isEmpty()) {
            query.setParameter("code", temp.getCode());
        }
        List<BookEntity> books = query.getResultList();
        return books;

    }

    public void updateBook(String isbn13, String isbn10, String title, String price) throws Exception {
        try {
            loadBook(isbn13);
        } catch (Exception e) {
            throw new Exception();
        }
        String queryString = "UPDATE BookEntity b SET b.isbn13 = :isbn13,b.isbn10 = :isbn10,b.title = :title, b.price = :price WHERE b.isbn13 = :isbn13";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("isbn13", isbn13);
        query.setParameter("isbn10", isbn10);
        query.setParameter("title", title);
        query.setParameter("price", price);
        query.executeUpdate();
    }

}
