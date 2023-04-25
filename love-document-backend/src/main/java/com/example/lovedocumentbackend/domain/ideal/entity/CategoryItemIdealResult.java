package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="category_item_ideal_result")
@Entity
public class CategoryItemIdealResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String start;

    private String mid;

    private String end;

    private Integer location;

    @OneToOne
    @JoinColumn(name = "category_item_id")
    private CategoryItem categoryItem;
}
