package com.example.lovedocumentbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionApiRequest {
    @Schema(description = "아이템 ID값 리스트", example = "[1,2,3,4]")
    private List<Long> categoryItems;
}
