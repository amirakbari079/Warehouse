package com.example.warehouse.manager;

import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.dao.CategoryDao;
import com.example.warehouse.dto.CategorySearchParamsDto;
import com.example.warehouse.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryManager {

    @Autowired
    CategoryDao categoryDao;

    public CategoryEntity save(CategoryEntity category) {
        return categoryDao.save(category);
    }

    public CategoryEntity loadByCode(String code) throws CategoryNotFoundException {
        return categoryDao.loadByCode(code);
    }

    public CategoryEntity updateCategory(String code, String subject) throws CategoryNotFoundException {
        CategoryEntity category = loadByCode(code);
        category.setSubject(subject);
        return category = categoryDao.update(category);
    }

    public void deleteCategory(String code) throws CustomException, CategoryNotFoundException {
        categoryDao.delete(code);
    }

    public List<CategoryEntity> searchCategory(CategorySearchParamsDto searchParamsDto) {
        return categoryDao.search(searchParamsDto.getSubject(),searchParamsDto.getCode(),searchParamsDto.getOrderBy(),searchParamsDto.getSortDirection());
    }
}
