package com.example.lovedocumentbackend.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString(exclude = {"categoryItem"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="category_item_example")
@Entity
public class CategoryItemExample extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private CategoryItem categoryItem;
}
