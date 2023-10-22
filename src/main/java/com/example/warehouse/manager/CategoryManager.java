package com.example.warehouse.manager;
import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.Exception.CustomException;
import com.example.warehouse.dao.CategoryDao;
import com.example.warehouse.dto.CategorySearchParamsDto;
import com.example.warehouse.entity.CategoryEntity;
import com.example.warehouse.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryManager {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryMapper categoryMapper;

    public CategoryEntity save(CategoryEntity category) {
        category.setCode(UUID.randomUUID().toString().substring(0, 5));
        return categoryDao.save(category);
    }

    public CategoryEntity loadByCode(String code) throws CategoryNotFoundException {
        return categoryDao.loadByCode(code);
    }
    public CategoryEntity loadBySubject(String subject) throws CategoryNotFoundException {
        try {
            return categoryDao.loadBySubject(subject);
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException();
        }
    }

    public CategoryEntity updateCategory(String code, String subject) throws CategoryNotFoundException {
        CategoryEntity category = loadByCode(code);
        category.setSubject(subject);
        return categoryDao.update(category);
    }

    public void deleteCategory(String code) throws CustomException, CategoryNotFoundException {
        categoryDao.delete(code);
    }

    public List<CategoryEntity> searchCategory(CategorySearchParamsDto searchParamsDto) {
        return categoryDao.search(searchParamsDto.getSubject(),searchParamsDto.getCode(),searchParamsDto.getOrderBy(),searchParamsDto.getSortDirection());
    }

    public CategoryEntity createCategory(String subject){
        return CategoryEntity.builder().subject(subject).code(UUID.randomUUID().toString().substring(0, 5)).build();
    }


}
