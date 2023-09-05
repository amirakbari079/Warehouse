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

    public void loadByCode(String code) {
        categoryDao.loadByCode(code);
    }

}
