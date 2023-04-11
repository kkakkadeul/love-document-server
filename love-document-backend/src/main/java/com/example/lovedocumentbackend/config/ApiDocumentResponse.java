package com.example.lovedocumentbackend.config;

import com.example.lovedocumentbackend.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "데이터를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(
                responseCode = "401",
                description = "인증 실패",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),

})
public @interface ApiDocumentResponse {
}
