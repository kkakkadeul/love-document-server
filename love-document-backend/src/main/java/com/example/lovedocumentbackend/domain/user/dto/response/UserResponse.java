package com.example.lovedocumentbackend.domain.user.dto.response;

import com.example.lovedocumentbackend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "유저 닉네임", example = "보니")
    private String nickname;

    @Schema(description = "유저 JWT 토큰", example = "sdfjhqklndklmsd;lm;lsd;lsdmv")
    private String token;

}
