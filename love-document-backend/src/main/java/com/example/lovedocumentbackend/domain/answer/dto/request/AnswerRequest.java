package com.example.lovedocumentbackend.domain.answer.dto.request;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.util.List;

@Getter
public class AnswerRequest {

    @Schema(description = "질문지 번호", example = "1")
    private Long questionId;

    private String nickname;

    private String age;

    private String work;

    private String live;

    private List<Answer> answerList;

    @Getter
    static public class Answer {

        @Schema(description = "카테고리 아이템 아이디", example = "1")
        private Long categoryItemId;

        @Schema(description = "카테고리 타입", example = "RANGE")
        private QuestionType questionType;

        @Schema(description = "객관식 아이디 리스트", example = "[1,2,3]")
        private List<Long> choiceIdList;

        @Schema(description = "키값 입력", example = "170")
        private Integer number;

        @Schema(description = "점수", example = "4")
        private Integer score;

        @Schema(description = "Y or N", example = "N")
        @Enumerated(EnumType.STRING)
        private BooleanType yn;
    }
}
