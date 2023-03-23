package com.example.lovedocumentbackend.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {

    RANGE(0, "범위","범위 질문" ),
    SCORE(1, "점수", "점수 질문"),
    YN(3, "Yes or No", "이지 선다 질문"),
    CHOICE(4, "객관식", "객관식 질문");

    private Integer id;
    private String title;
    private String description;
}
