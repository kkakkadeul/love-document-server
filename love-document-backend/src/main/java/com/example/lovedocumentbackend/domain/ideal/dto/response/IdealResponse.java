package com.example.lovedocumentbackend.domain.ideal.dto.response;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdealResponse {

    @Schema(description = "카테고리 title", example = "외모")
    private String categoryTitle;

    @Schema(description = "내 기준 정보 리스트")
    private List<IdealInfo> idealInfoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class IdealInfo{

        @Schema(description = "카테고리 아이템 title", example = "타투")
        private String categoryTitle;

        @Schema(description = "내 기준 답안", example = "타투가 없는 사람을 원해요")
        private String ideal;
    }
}
