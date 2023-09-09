package com.example.warehouse.manager;

import com.example.warehouse.dao.CategoryDao;
import com.example.warehouse.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryManager {
    @Autowired
    CategoryDao categoryDao;

    public void save(CategoryEntity category) {
        categoryDao.save(category);
    }

    public CategoryEntity loadByCode(String code) {
        return categoryDao.loadByCode(code);
    }

    public CategoryEntity updateCategory(String code, String subject) {
        CategoryEntity category = loadByCode(code);
        category.setSubject(subject);
        return category = categoryDao.update(category);
        //TODO Handel the not-found exception
    }

    public void deleteCategory(String code) {
        categoryDao.delete(code);
    }
}
