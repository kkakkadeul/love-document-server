package com.example.lovedocumentbackend.domain.user.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude = {"questionGroupList"})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QuestionGroup> questionGroupList;
}
