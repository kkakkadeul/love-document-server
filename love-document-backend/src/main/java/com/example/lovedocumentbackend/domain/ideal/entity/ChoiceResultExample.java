package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="choice_result_example")
@Entity
public class ChoiceResultExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idealResult;

    private String answerResult;

    @OneToOne
    @JoinColumn(name="category_item_example_id")
    private CategoryItemExample categoryItemExample;
}
