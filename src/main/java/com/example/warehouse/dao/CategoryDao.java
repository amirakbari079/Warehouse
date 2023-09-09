package com.example.warehouse.dao;

import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Slf4j
@Component
public class CategoryDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void save(CategoryEntity category) {
        entityManager.persist(category);
        log.info("category is saved to database. {}", category);
    }

    @Transactional
    public CategoryEntity loadByCode(String code) {
        String queryString = "select c from CategoryEntity c where c.code = :code";
        TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
        typedQuery.setParameter("code", code);
        CategoryEntity category = typedQuery.getSingleResult();
        return category;
    }

    @Transactional
    public CategoryEntity update(CategoryEntity category) {
        entityManager.merge(category);
        return category;

    }

    @Transactional
    public void delete(String code) {
        CategoryEntity category = loadByCode(code);
        if (category != null) {
            entityManager.remove(category);
        }
        System.out.println("Entity not found in the database");
    }
    //TODO Implementing Not Found Exception


}
