package com.example.warehouse.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDtoPage {
    private List<CategoryDto> items;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
