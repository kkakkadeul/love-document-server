package com.example.lovedocumentbackend.domain.question.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionNicknameResponse {
    @Schema(description = "질문자 닉네임", example = "키위")
    private String nickname;
}
