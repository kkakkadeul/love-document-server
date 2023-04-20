package com.example.lovedocumentbackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NicknameCheckRequest {

    @Schema(description = "유저 닉네임", example = "보니")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp="^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{1,10}",
            message = "특수문자를 제외한 1자 ~ 10자의 닉네임이여야 합니다.")
    private String nickname;
}
