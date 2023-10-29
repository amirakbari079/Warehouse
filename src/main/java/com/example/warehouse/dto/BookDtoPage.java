package com.example.warehouse.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoPage {
    private List<BookDto> items;
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalCount;
}
