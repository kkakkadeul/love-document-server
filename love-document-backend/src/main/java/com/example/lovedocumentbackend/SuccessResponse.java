package com.example.lovedocumentbackend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

    @Schema(description = "성공 메세지", example = "사용 가능한 닉네임이예요.")
    private String message;
}
