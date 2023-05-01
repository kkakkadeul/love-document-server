package com.example.lovedocumentbackend.domain.answer.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

    @Schema(description = "답변자 닉네임", example = "키위")
    private String nickname;
    @Schema(description = "답변자 나이", example = "28")
    private String age;
    @Schema(description = "답변자 사는곳", example = "서울 강남구")
    private String live;
    @Schema(description = "답변자 직업", example = "개발자")
    private String work;
    @Schema(description = "답변자 닉네임", example = "키위")
    private LocalDateTime dateTime;
    @Schema(description = "상대방과 맞는 대답 퍼센테이지", example = "91")
    private Integer percentage;
    @Schema(description = "총 질문", example = "11")
    private Integer totalCnt;
    @Schema(description = "맞는 대답", example = "10")
    private Integer matchCnt;
    @Schema(description = "다른 대답이 나온 카테고리 아이템 제목 리스트", example = "[키, 타투, 만남빈도]")
    private Set<String> nonMatchTitleList;
    private List<CategoryInfo> categoryInfoList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CategoryInfo{
        @Schema(description = "카테고리 이모티콘", example = "\uD83D\uDC40")
        private String emoji;
        @Schema(description = "카테고리 제목", example = "외모")
        private String title;
        private List<Item> itemList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Item{
        private AnswerInfo answerInfo;
        @Schema(description = "질문자의 이상형", example = "저는 안경을 안 쓴 사람을 원해요")
        private String ideal;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class AnswerInfo{
        @Schema(description = "시작 문자열", example = "저는")
        private String start;
        @Schema(description = "중간 문자열", example = "null")
        private String mid;
        @Schema(description = "끝 문자열", example = "null")
        private String end;
        @Schema(description = "답안 위치", example = "2")
        private Integer location;
        @Schema(description = "답안", example = "안경을 써요")
        private String answer;
    }

}
