package com.example.warehouse.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WH_Book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String isbn10;
    @NotNull
    @Column(unique = true)
    private String isbn13;
    @NotNull
    private String price;
    @NotNull
    private String title;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    private List<CategoryEntity> categories;

}


