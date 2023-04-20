package com.example.lovedocumentbackend.domain.question.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import com.example.lovedocumentbackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "questionGroup")
    private Ideal ideal;
}
