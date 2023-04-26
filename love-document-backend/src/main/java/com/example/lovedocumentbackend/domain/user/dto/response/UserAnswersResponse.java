package com.example.lovedocumentbackend.domain.user.dto.response;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswersResponse {

    @Schema(description = "답변자 닉네임", example = "망고")
    private String nickname;

    @Schema(description = "답변자 나이", example = "28")
    private String age;

    @Schema(description = "답변자 사는곳", example = "서울 강남구")
    private String live;

    @Schema(description = "백분율 표시 유무", example = "N")
    private BooleanType show;

    @Schema(description = "일치 퍼센트", example = "70")
    private Integer percentage;

}
