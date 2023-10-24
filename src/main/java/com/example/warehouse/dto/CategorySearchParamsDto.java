package com.example.warehouse.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySearchParamsDto {
    private String subject;
    private String code;
    private Integer pageSize;
    private Integer pageNumber;
    private String orderBy;
    private String sortDirection;
}
