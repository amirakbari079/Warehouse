package com.example.warehouse.dto;

import com.example.warehouse.entity.BookEntity;
import lombok.Data;
import java.util.List;

@Data
public class BookDtoPage {
    private List<BookDto> items;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
