package com.example.lovedocumentbackend.domain.answer.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="answer")
@Entity
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private BooleanType userShow;

    private String nickname;

    private String age;

    private String work;

    private String live;

    @Setter
    @Enumerated(EnumType.STRING)
    private BooleanType status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    private List<AnswerYn> answerYnList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    private List<AnswerScore> answerScoreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    private List<AnswerNumber> answerNumberList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    private List<AnswerChoice> answerChoiceList;

    @ManyToOne
    @JoinColumn(name = "question_group_id")
    private QuestionGroup questionGroup;
}
