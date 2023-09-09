package com.example.warehouse.mapper;

import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    CategoryEntity categoryEntity = new CategoryEntity();

    public CategoryEntity toEntity(CategoryDto categoryDto) {
        categoryEntity.setSubject(categoryDto.getSubject());
        return categoryEntity;
    }


    CategoryDto categoryDto = new CategoryDto();

    public CategoryDto toDto(CategoryEntity categoryEntity) {
        categoryDto.setCode(categoryEntity.getCode());
        categoryDto.setSubject(categoryEntity.getSubject());
        return categoryDto;
    }
}
