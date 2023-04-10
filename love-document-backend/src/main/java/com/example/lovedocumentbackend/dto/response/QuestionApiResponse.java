package com.example.lovedocumentbackend.dto.response;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionApiResponse {

    @Schema(description = "카테고리 title", example = "외모")
    private String categoryTitle;

    @Schema(description = "카테고리 아이템 내용")
    private List<CategoryItemInfo> categoryItemInfoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CategoryItemInfo{

        @Schema(description = "아이템 id", example = "1")
        private Long id;

        @Schema(description = "중복 정답 유무", example = "N")
        @Enumerated(EnumType.STRING)
        private BooleanType multiple;

        @Schema(description = "질문 유형", example = "RANGE")
        @Enumerated(EnumType.STRING)
        private QuestionType type;

        @Schema(description = "질문", example = "원하는 키는?")
        private String question;

        @Schema(description = "부정적 라벨", example = "싫어요")
        private String negativeLabel;

        @Schema(description = "긍정적 라벨", example = "좋아요")
        private String positiveLabel;

        @Schema(description = "객관식 답 예시 리스트")
        private List<Example> exampleList;

        @Schema(description = "CHOICE id 리스트 or null", example = "[1, 2, 3]")
        private List<Long> choiceIdList;

        @Schema(description = "RANGE int 리스트 or null ", example = "[160, 180]")
        private List<Integer> rangeNumList;

        @Schema(description = "SCORE int 리스트 or null ", example = "[3]")
        private List<Integer> scoreNumList;

        @Schema(description = "YN string 리스트 or null ", example = "[Y]")
        private List<BooleanType> ynBoolList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Example{
        @Schema(description = "객관식 id", example = "1")
        private Long id;

        @Schema(description = "객관식 예시", example = "기독교")
        private String content;
    }
}
