package com.example.lovedocumentbackend.ideal.dto.request;


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
        private List<Integer> rangeNumList;

        @Schema(description = "점수 숫자 리스트(1개)", example = "[4]")
        private List<Integer> scoreNumList;

        @Schema(description = "예스 올 노우 리스트(1개)", example = "[N]")
        private List<BooleanType> ynBoolList;
    }
}
