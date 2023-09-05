package com.example.warehouse.mapper;

import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.entity.CategoryEntity;
import jdk.jfr.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    CategoryEntity categoryEntity=new CategoryEntity();
    public CategoryEntity mapper(CategoryDto categoryDto){
        categoryEntity.setSubject(categoryDto.getSubject());
        return categoryEntity;
    }
}
