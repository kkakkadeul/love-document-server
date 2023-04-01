package com.example.lovedocumentbackend.entity;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"categoryItem", "questionGroup"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BooleanType status;

    @ManyToOne
    private CategoryItem categoryItem;

    @ManyToOne
    private QuestionGroup questionGroup;

    public Category getCategory() {
        return this.getCategoryItem().getCategory();
    }

    public Long getCategoryItemId() {
        return this.categoryItem.getId();
    }

    public Long getCategoryId() {
        return getCategory().getId();
    }
}
