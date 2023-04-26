package com.example.lovedocumentbackend.domain.category.entity;


import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.answer.entity.AnswerChoice;
import com.example.lovedocumentbackend.domain.ideal.entity.ChoiceResultExample;
import com.example.lovedocumentbackend.domain.ideal.entity.IdealChoice;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@ToString(exclude = {"categoryItem"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="category_item_example")
@Entity
public class CategoryItemExample extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private CategoryItem categoryItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItemExample")
    private List<IdealChoice> idealChoiceList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "categoryItemExample")
    private ChoiceResultExample choiceResultExample;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItemExample")
    private List<AnswerChoice> answerChoiceList;
}
