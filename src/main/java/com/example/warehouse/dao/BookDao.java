package com.example.warehouse.dao;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
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

    public void save(BookEntity book) throws CustomException {
        try {
            entityManager.persist(book);
            log.info("book is saved to database. {}", book);
        }catch (Exception e){
            throw new CustomException();
        }
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
    public void deleteBook(String isbn13) throws Exception {
        try {
            BookEntity book=loadBook(isbn13);
            entityManager.remove(book);
        } catch (Exception e) {
            throw new Exception("There is no book with this isbn13");
        }

    }
}
