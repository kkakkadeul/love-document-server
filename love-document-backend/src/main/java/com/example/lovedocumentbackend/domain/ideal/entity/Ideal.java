package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude ={"questionGroup", "idealYnList", "idealRangeList", "idealScoreList"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="ideal")
@Entity
public class Ideal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_group_id")
    private QuestionGroup questionGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal", orphanRemoval = true)
    private List<IdealYn> idealYnList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal", orphanRemoval = true)
    private List<IdealRange> idealRangeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal", orphanRemoval = true)
    private List<IdealScore> idealScoreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal", orphanRemoval = true)
    private List<IdealChoice> idealChoiceList;
}
