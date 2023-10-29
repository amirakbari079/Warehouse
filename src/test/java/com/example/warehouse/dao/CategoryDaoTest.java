package com.example.warehouse.dao;
import com.example.warehouse.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
class CategoryDaoTest {

    @Autowired
    CategoryDao categoryDao;

    @Test
    @Transactional
    void save_successfully_oneCategoryEntity() {
        CategoryEntity category=new CategoryEntity(null,"aw2dd","test",null);
        try {
            categoryDao.save(category);
        }catch (Exception e){
            fail();
        }
    }

}