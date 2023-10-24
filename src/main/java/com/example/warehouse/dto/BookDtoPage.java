package com.example.warehouse.dto;
import java.util.List;
import lombok.Data;

@Data
public class BookDtoPage {
    private List<BookDto> items;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
