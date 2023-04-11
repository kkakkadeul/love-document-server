package com.example.lovedocumentbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.lovedocumentbackend.domain.question.controller.QuestionApiController;
import com.example.lovedocumentbackend.domain.question.dto.response.QuestionApiResponse;
import com.example.lovedocumentbackend.domain.question.service.QuestionApiLogicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class QuestionApiControllerTest {

    @Mock
    private QuestionApiLogicService questionApiLogicService;

    @InjectMocks
    private QuestionApiController questionApiController;

    private Authentication mockAuthentication;

    @BeforeEach
    public void setUp() {
        mockAuthentication = mock(Authentication.class);
    }

    @Test
    @DisplayName("유저 질문 불러오기 테스트")
    public void 유저_질문_불러오기_테스트() {
        // given
        String nickname = "test01";
        List<QuestionApiResponse> expectedQuestionList = List.of(
                new QuestionApiResponse("", List.of(new QuestionApiResponse.CategoryItemInfo()))
        );
        when(questionApiLogicService.getIdeal(any())).thenReturn(expectedQuestionList);

        // when
        ResponseEntity<List<QuestionApiResponse>> responseEntity = questionApiController.getIdealQuestion(mockAuthentication);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedQuestionList, responseEntity.getBody());
    }

//    @Test
//    public void postQuestion_shouldReturnTrueWhenQuestionsAreMadeSuccessfully() {
//        // given
//        String username = "testuser";
//        List<QuestionApiRequest> requestList = Arrays.asList(
//                new QuestionApiRequest("What's your favorite color?"),
//                new QuestionApiRequest("What's your favorite food?")
//        );
//        when(questionApiLogicService.makeQuestions(anyString(), anyList())).thenReturn(true);
//
//        // when
//        ResponseEntity<Boolean> responseEntity = questionApiController.postQuestion(mockAuthentication, requestList);
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(true, responseEntity.getBody());
//    }
}