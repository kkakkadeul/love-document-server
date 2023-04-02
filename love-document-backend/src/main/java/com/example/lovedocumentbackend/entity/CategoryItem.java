package com.example.lovedocumentbackend.entity;


import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.ideal.entity.IdealRange;
import com.example.lovedocumentbackend.ideal.entity.IdealScore;
import com.example.lovedocumentbackend.ideal.entity.IdealYn;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@ToString(exclude = {"category", "categoryItemExampleList", "idealYnList","idealRangeList", "idealScoreList"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="category_item")
public class CategoryItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BooleanType idealMultiple;

    @Enumerated(EnumType.STRING)
    private BooleanType answerMultiple;

    private String title;

    private String idealQuestion;

    private String answerQuestion;

    private String idealNegativeLabel;

    private String idealPositiveLabel;

    private String answerNegativeLabel;

    private String answerPositiveLabel;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private List<CategoryItemExample> categoryItemExampleList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private List<IdealYn> idealYnList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private List<IdealRange> idealRangeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private List<IdealScore> idealScoreList;

}
