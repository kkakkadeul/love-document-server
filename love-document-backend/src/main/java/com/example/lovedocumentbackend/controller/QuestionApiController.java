package com.example.lovedocumentbackend.controller;


import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.dto.request.QuestionApiRequest;
import com.example.lovedocumentbackend.dto.response.QuestionApiResponse;
import com.example.lovedocumentbackend.service.QuestionApiLogicService;
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

@Tag(name = "Question", description = "질문 관련 api")
@RequiredArgsConstructor
@RequestMapping("/questions")
@RestController
public class QuestionApiController {

    private final QuestionApiLogicService questionApiLogicService;

    @ApiDocumentResponse
    @Operation(summary = "유저 질문", description = "유저 질문 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = QuestionApiResponse.class)))}),
    })
    @GetMapping("")
    public ResponseEntity<List<QuestionApiResponse>> getIdealQuestion(Authentication authentication) {
        return new ResponseEntity<>(questionApiLogicService.getIdeal(authentication.getName()), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "카테고리 목록", description = "카테고리 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
    })
    @PostMapping("")
    public ResponseEntity<Void> postQuestion(Authentication authentication, @RequestBody QuestionApiRequest request) {
        questionApiLogicService.firstMakeQuestions(authentication.getName(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
