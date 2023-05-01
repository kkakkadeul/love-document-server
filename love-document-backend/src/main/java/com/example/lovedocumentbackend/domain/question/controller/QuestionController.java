package com.example.lovedocumentbackend.domain.question.controller;


import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.question.dto.request.QuestionRequest;
import com.example.lovedocumentbackend.domain.question.dto.response.AnswerQuestionResponse;
import com.example.lovedocumentbackend.domain.question.dto.response.QuestionNicknameResponse;
import com.example.lovedocumentbackend.domain.question.dto.response.QuestionResponse;
import com.example.lovedocumentbackend.domain.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question", description = "question api")
@RequiredArgsConstructor
@RequestMapping("/questions")
@RestController
public class QuestionController {

    private final QuestionService questionApiLogicService;

    @ApiDocumentResponse
    @Operation(summary = "답변자 질문지 조회", description = "답변자의 질문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = AnswerQuestionResponse.class)))}),
    })
    @GetMapping("{questionId}")
    public ResponseEntity<List<AnswerQuestionResponse>> getAnswerQuestion(@PathVariable Long questionId) {
        return new ResponseEntity<>(questionApiLogicService.getAnswer(questionId), HttpStatus.OK);
    }
    @ApiDocumentResponse
    @Operation(summary = "답변자 질문지 조회", description = "답변자의 질문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = AnswerQuestionResponse.class)))}),
    })
    @GetMapping("{questionId}/nickname")
    public ResponseEntity<QuestionNicknameResponse> getQuestionNickname(@PathVariable Long questionId) {
        return new ResponseEntity<>(questionApiLogicService.getQuestionNickname(questionId), HttpStatus.OK);
    }
    @ApiDocumentResponse
    @Operation(summary = "유저 질문지 조회", description = "유저가 선택한 카테고리의 질문지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = QuestionResponse.class)))}),
    })
    @GetMapping("")
    public ResponseEntity<List<QuestionResponse>> getIdealQuestion(Authentication authentication) {
        return new ResponseEntity<>(questionApiLogicService.getIdeal(authentication.getName()), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "유저 질문지 생성", description = "유저가 선택한 카테고리로 질문지 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
    })
    @PostMapping("")
    public ResponseEntity<Void> postQuestion(Authentication authentication, @RequestBody QuestionRequest request) {
        questionApiLogicService.firstMakeQuestions(authentication.getName(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
