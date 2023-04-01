package com.example.lovedocumentbackend.controller;


import com.example.lovedocumentbackend.dto.request.QuestionApiRequest;
import com.example.lovedocumentbackend.dto.response.QuestionApiResponse;
import com.example.lovedocumentbackend.service.QuestionApiLogicService;
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

    @GetMapping("")
    public ResponseEntity<List<QuestionApiResponse>> getIdealQuestion(Authentication authentication) {
        return new ResponseEntity<>(questionApiLogicService.getIdeal(authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> postQuestion(Authentication authentication, @RequestBody List<QuestionApiRequest> requestList) {
            return new ResponseEntity<>(questionApiLogicService.makeQuestions(authentication.getName(), requestList), HttpStatus.OK);
    }
}
