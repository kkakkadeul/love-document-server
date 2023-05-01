package com.example.lovedocumentbackend.domain.answer.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.answer.dto.request.AnswerRequest;
import com.example.lovedocumentbackend.domain.answer.dto.response.AnswerResponse;
import com.example.lovedocumentbackend.domain.answer.service.AnswerService;
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

@Tag(name = "Answer", description = "Answer api")
@RequiredArgsConstructor
@RequestMapping("/answers")
@RestController
public class AnswerController {
    private final AnswerService answerService;

    @ApiDocumentResponse
    @Operation(summary = "답변자 답변 생성", description = "답변자 답변 저장하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
    })
    @PostMapping("")
    public ResponseEntity<Void> postAnswer(@RequestBody AnswerRequest request){
        answerService.saveAnswer(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "답변자 답변 조회", description = "답변자 답변 결과 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = AnswerResponse.class)))}),
    })
    @GetMapping("{answerId}")
    public ResponseEntity<AnswerResponse> getAnswer(@PathVariable Long answerId, Authentication authentication){
        return new ResponseEntity<>(answerService.getAnswer(answerId, authentication.getName()),HttpStatus.OK);
    }
}
