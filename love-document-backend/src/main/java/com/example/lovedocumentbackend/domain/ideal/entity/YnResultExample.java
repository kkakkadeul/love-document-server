package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="yn_result_example")
public class YnResultExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BooleanType answer;

    private String idealResult;

    private String answerResult;

    @ManyToOne
    @JoinColumn(name="category_item_id")
    private CategoryItem categoryItem;
}
