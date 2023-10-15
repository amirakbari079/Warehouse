package com.example.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItBookResponseDto {
    private String isbn10;
    private String isbn13;
    private String price;
    private String title;

}
