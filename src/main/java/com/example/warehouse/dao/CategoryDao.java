package com.example.warehouse.dao;

import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.dto.CategorySearchParamsDto;
import com.example.warehouse.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.util.List;

@Slf4j
@Component
public class CategoryDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public CategoryEntity save(CategoryEntity category) {
        entityManager.persist(category);
        String queryString="select c from CategoryEntity c where c.subject = :subject";
        TypedQuery<CategoryEntity> typedQuery=entityManager.createQuery(queryString,CategoryEntity.class);
        typedQuery.setParameter("subject",category.getSubject());
        return typedQuery.getSingleResult();
//        log.info("category is saved to database. {}", category);
    }

    @Transactional
    public CategoryEntity loadByCode(String code) throws CategoryNotFoundException{
        try {
            String queryString = "select c from CategoryEntity c where c.code = :code";
            TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(queryString, CategoryEntity.class);
            typedQuery.setParameter("code", code);
            return typedQuery.getSingleResult();
        }catch (Exception e){
           throw new CategoryNotFoundException();
        }

    }

    @Transactional
    public CategoryEntity update(CategoryEntity category) throws CategoryNotFoundException {
        try{
            entityManager.merge(category);
            return category;
        }catch (Exception e){
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
    public List<CategoryEntity> search(String subject, String code, String oderBy, String sortDirction) {
        String conditioinQuery = "";
        if (code != null && !code.isEmpty()) {
            conditioinQuery = " AND e.code= :code";
        }
        if (subject != null && !subject.isEmpty()) {
            conditioinQuery += " AND e.subject LIKE :subject";
        }
        String queryString = "SELECT e FROM CategoryEntity e WHERE 1=1" + conditioinQuery + " ORDER BY " + oderBy.toLowerCase() + " " + sortDirction;
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


//    @Transactional
//    public List<CategoryEntity> search(String subject,String code) {
//        String queryString = "SELECT e FROM CategoryEntity e WHERE e.subject LIKE :fieldValue";
//        TypedQuery<CategoryEntity> query = entityManager.createQuery(queryString, CategoryEntity.class);
//        query.setParameter("fieldValue", "%" + subject + "%");
//        List<CategoryEntity> CategoryEntityList = query.getResultList();
//        return CategoryEntityList;
//    }

}


