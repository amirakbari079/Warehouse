package com.example.warehouse.mapper;

import com.example.warehouse.dto.CategoryDto;
import com.example.warehouse.dto.CategoryDtoPage;
import com.example.warehouse.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public CategoryEntity toEntity(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setSubject(categoryDto.getSubject());
        return categoryEntity;
    }


    public CategoryDto toDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCode(categoryEntity.getCode());
        categoryDto.setSubject(categoryEntity.getSubject());
        return categoryDto;
    }


    public CategoryDtoPage categoryListToDto(List<CategoryEntity> categoryEntity){
        CategoryDtoPage categoryDtoPage=new CategoryDtoPage();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (int i=0;i<categoryEntity.size();i++){
            categoryDtoList.add(toDto(categoryEntity.get(i)));
        }
        categoryDtoPage.setItems(categoryDtoList);
        categoryDtoPage.setTotalCount((long) categoryDtoList.size());

        return categoryDtoPage;
    }
}
