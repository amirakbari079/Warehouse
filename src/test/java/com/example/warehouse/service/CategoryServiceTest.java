package com.example.warehouse.service;

import com.example.warehouse.Exception.NullFieldException;
import com.example.warehouse.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {


    CategoryService categoryService=new CategoryService();
    CategoryDto category=new CategoryDto("e1fb6",null);
    @Test
    @Transactional
    void update_nullSubject_NullFieldException() {
        assertThrows(NullFieldException.class,()->categoryService.update("e1fb6",category));
    }
}A