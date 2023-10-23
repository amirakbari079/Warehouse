package com.example.warehouse.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WH_Category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String subject;
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "categories")
    private List<BookEntity> books;

    @Override
    public String toString() {
        return "CategoryEntity.CategoryEntityBuilder(id=" + this.id + ", code=" + this.code + ", subject=" + this.subject + ")";
    }
}
