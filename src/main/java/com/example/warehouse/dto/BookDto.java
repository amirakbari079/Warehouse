package com.example.warehouse.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String price;
    private String isbn10;
    private String isbn13;
    private List<CategoryDto> categories;
}
