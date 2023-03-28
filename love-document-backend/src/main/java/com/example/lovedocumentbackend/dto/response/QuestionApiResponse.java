package com.example.lovedocumentbackend.dto.response;

import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionApiResponse {

    private String categoryTitle;

    private List<CategoryItemInfo> categoryItemInfoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CategoryItemInfo{

        private Long id;

        @Enumerated(EnumType.STRING)
        private BooleanType multiple;

        @Enumerated(EnumType.STRING)
        private QuestionType type;

        private String question;

        private List<String> contentList;

    }
}
