package com.example.warehouse.dto;

import java.util.List;

public class CategoryDtoPage {
    private List<CategoryDto> items;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
