package com.example.lovedocumentbackend.domain.user.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    @Schema(description = "유저 닉네임", example = "보니")
    private String nickname;

    @Schema(description = "선택 카테고리 개수", example = "4")
    private Integer categoryNum;

    @Schema(description = "링크 아이디 값", example = "1")
    private Long linkId;
}
