package com.example.lovedocumentbackend.domain.user.dto.response;


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
public class UserCategoryResponse {

    @Schema(description = "카테고리 이름", example = "외모")
    private String categoryTitle;

    @Schema(description = "카테고리 아이템 리스트", example = "[타투, 키, 안경]")
    private List<String> categoryItemList;
}
