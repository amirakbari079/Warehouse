package com.example.warehouse.dao;
import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
public class CategoryDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public CategoryEntity save(CategoryEntity category) {

        entityManager.persist(category);
        String queryString = "select c from CategoryEntity c where c.subject = :subject";
        TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
        typedQuery.setParameter("subject", category.getSubject());
        return typedQuery.getSingleResult();
    }

    @Transactional
    public CategoryEntity loadByCode(String code) throws CategoryNotFoundException {
        try {
            String queryString = "select c from CategoryEntity c where c.code = :code";
            TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
            typedQuery.setParameter("code", code);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

    }

    @Transactional
    public CategoryEntity loadBySubject(String subject) throws CategoryNotFoundException {
        try {
            String queryString = "select c from CategoryEntity c where c.subject = :subject";
            TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
            typedQuery.setParameter("subject", subject);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }

    }

    @Transactional
    public CategoryEntity update(CategoryEntity category) throws CategoryNotFoundException {
        try {
            entityManager.merge(category);
            return category;
        } catch (Exception e) {
            throw new CategoryNotFoundException();
        }


    }

    @Transactional
    public void delete(String code) throws CustomException, CategoryNotFoundException {
        CategoryEntity category = loadByCode(code);
        try {
            entityManager.remove(category);
        } catch (NullPointerException nullPointerException) {
            throw new CategoryNotFoundException();
        }
    }

    @Transactional
    public List<CategoryEntity> search(String subject, String code, String oderBy, String sortDirection) {
        String conditionQuery = "";
        if (code != null && !code.isEmpty()) {
            conditionQuery = " AND e.code= :code";
        }
        if (subject != null && !subject.isEmpty()) {
            conditionQuery += " AND e.subject LIKE :subject";
        }
        String queryString = "SELECT e FROM CategoryEntity e WHERE 1=1" + conditionQuery + " ORDER BY " + oderBy.toLowerCase() + " " + sortDirection;
        TypedQuery<CategoryEntity> query = entityManager.createQuery(queryString, CategoryEntity.class);
        if (code != null && !code.isEmpty()) {
            query.setParameter("code", code);
        }
        if (subject != null && !subject.isEmpty()) {
            query.setParameter("subject", "%" + subject + "%");
        }
        List<CategoryEntity> CategoryEntityList = query.getResultList();
        return CategoryEntityList;

    }
}


