package com.example.lovedocumentbackend.entity;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"user"})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="question_group")
@Entity
public class QuestionGroup extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String link;

    private Integer itemNum;

    @Enumerated(EnumType.STRING)
    private BooleanType status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
