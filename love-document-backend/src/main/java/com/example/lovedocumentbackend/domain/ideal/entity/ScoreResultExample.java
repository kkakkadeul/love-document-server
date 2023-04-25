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
@Table(name="score_result_example")
@Entity
public class ScoreResultExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer answer;

    private String idealResult;

    private String answerResult;

    @ManyToOne
    @JoinColumn(name="category_item_id")
    private CategoryItem categoryItem;
}
