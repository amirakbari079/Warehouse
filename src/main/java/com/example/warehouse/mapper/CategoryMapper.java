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
        categoryEntity.setSubject(categoryDto.getSubject().trim());
        return categoryEntity;
    }


    public CategoryDto toDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCode(categoryEntity.getCode());
        categoryDto.setSubject(categoryEntity.getSubject());
        return categoryDto;
    }


    public CategoryDtoPage categoryListEntityToDtoPage(List<CategoryEntity> categoryEntity, Integer pageNumber, Integer pageSize) {
        CategoryDtoPage categoryDtoPage = new CategoryDtoPage();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (int i = 0; i < categoryEntity.size(); i++) {
            categoryDtoList.add(toDto(categoryEntity.get(i)));
        }
        categoryDtoPage.setItems(categoryDtoList);
        categoryDtoPage.setTotalCount((long) categoryDtoList.size());
        categoryDtoPage.setPageNumber(pageNumber);
        categoryDtoPage.setPageSize(pageSize);
        return categoryDtoPage;
    }


    public List<CategoryDto> categoryListEntityToDto(List<CategoryEntity> categoryEntity) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (int i = 0; i < categoryEntity.size(); i++) {
            categoryDtoList.add(toDto(categoryEntity.get(i)));
        }
        return categoryDtoList;
    }

    public List<CategoryEntity> categoryListDtoToEntity(List<CategoryDto> categoryDto) {
        List<CategoryEntity> categoryEntitiesList = new ArrayList<>();
        for (CategoryDto dto : categoryDto) {
            categoryEntitiesList.add(toEntity(dto));
        }
        return categoryEntitiesList;
    }


}
