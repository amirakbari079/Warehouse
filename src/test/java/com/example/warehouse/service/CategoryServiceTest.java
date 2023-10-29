package com.example.warehouse.service;
import com.example.warehouse.Exception.CategoryNotFoundException;
import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.entity.CategoryEntity;
import com.example.warehouse.manager.CategoryManager;
import com.example.warehouse.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    CategoryDto category=new CategoryDto();
    @Mock
    CategoryManager categoryManager;
    @Mock
    CategoryMapper CategoryMapper;
    @InjectMocks
    CategoryService categoryService;

    //----------------------------------Update method-----------------------------------------
    @Test
    @Transactional
    void update_successFully_200Response() throws CategoryNotFoundException {
        category.setCode("e1fb6");
        category.setSubject("editeCategory");
        CategoryEntity categoryEntity=new CategoryEntity(null,"e1fb6","editeCategory",null);
        Mockito.when(categoryManager.updateCategory("e1fb6","editeCategory")).thenReturn(categoryEntity);
        Mockito.when(CategoryMapper.toDto(categoryEntity)).thenReturn(category);
        assertEquals(200,categoryService.update("e1fb6",category).getStatus());
    }

    @Test
    @Transactional
    void update_nullSubject_422Response() {
        category.setCode("e1fb6");
        category.setSubject(null);
        assertEquals(422,categoryService.update("e1fb6",category).getStatus());
    }
    //-------------------------------------------------------------------------------------------
    //\/\/\/\\/\/\/\/\/\/\//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
}