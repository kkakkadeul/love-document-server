package com.example.lovedocumentbackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Schema(description = "유저 닉네임", example = "보니")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp="^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{1,10}",
            message = "특수문자를 제외한 1자 ~ 10자의 닉네임이여야 합니다.")
    private String nickname;

    @Schema(description = "패스워드", example = "1234")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]).{4,20}",
            message = "4자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}
