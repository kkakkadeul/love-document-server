package com.example.lovedocumentbackend.domain.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    @Schema(description = "카테고리 아이디", example = "1")
    private Long id;

    @Schema(description = "카테고리 이름", example = "외모")
    private String title;

    @Schema(description = "카테고리 아이템 리스트")
    private List<CategoryItemResponse> categoryItemList;

}
