package com.example.lovedocumentbackend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "U001")
    private final String code;

    @Schema(description = "에러 메세지", example = "유저를 찾을 수 없습니다.")
    private final String message;
}
