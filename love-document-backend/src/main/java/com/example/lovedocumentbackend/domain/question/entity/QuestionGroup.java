package com.example.lovedocumentbackend.domain.question.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.answer.entity.Answer;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import com.example.lovedocumentbackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude = {"user"})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="question_group")
@Entity
public class QuestionGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long linkId;

    private Integer itemNum;

    @Enumerated(EnumType.STRING)
    private BooleanType status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "questionGroup")
    private List<Ideal> idealList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "questionGroup")
    private List<Answer> answerList;
}
