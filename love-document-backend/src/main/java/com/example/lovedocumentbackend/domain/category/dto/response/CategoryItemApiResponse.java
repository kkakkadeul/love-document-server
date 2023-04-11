package com.example.lovedocumentbackend.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemApiResponse {

    @Schema(description = "카테고리 아이템 아이디", example = "1")
    private Long id;

    @Schema(description = "카테고리 아이템 이름", example = "키")
    private String title;
}
