package com.example.lovedocumentbackend.ideal.entity;

import com.example.lovedocumentbackend.entity.BaseEntity;
import com.example.lovedocumentbackend.entity.QuestionGroup;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal")
    private List<IdealYn> idealYnList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal")
    private List<IdealRange> idealRangeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal")
    private List<IdealScore> idealScoreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ideal")
    private List<IdealChoice> idealChoiceList;
}
