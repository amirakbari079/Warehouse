package com.example.warehouse.manager;

import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CategoryManagerTest {

    @InjectMocks
    CategoryManager categoryManager;

    @Test
    @Transactional
    void updateCategory_successfully_oneCategoryEntity() throws CategoryNotFoundException {
        CategoryEntity category =CategoryEntity.builder().subject("update").code("3e42e").build();
        Mockito.when(categoryManager.loadByCode("3e42e")).thenReturn(category);
        categoryManager.updateCategory("3e42ee","update");
    }
}