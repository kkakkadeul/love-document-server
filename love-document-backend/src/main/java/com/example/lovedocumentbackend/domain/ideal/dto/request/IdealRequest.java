package com.example.lovedocumentbackend.domain.ideal.dto.request;


import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class IdealRequest {

    private List<ideal> idealList;

    @Getter
    static public class ideal {

        @Schema(description = "카테고리 아이템 아이디", example = "1")
        private Long categoryItemId;

        @Schema(description = "카테고리 타입", example = "RANGE")
        private QuestionType questionType;

        @Schema(description = "객관식 아이디 리스트", example = "[1,2,3]")
        private List<Long> choiceIdList;

        @Schema(description = "범위 숫자 리스트(2개)", example = "[160,180]")
        private List<Integer> rangeList;

        @Schema(description = "점수", example = "4")
        private Integer score;

        @Schema(description = "Y or N", example = "N")
        private BooleanType yn;
    }
}
