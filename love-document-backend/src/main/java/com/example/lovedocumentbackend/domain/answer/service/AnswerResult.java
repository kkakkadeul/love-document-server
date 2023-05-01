package com.example.lovedocumentbackend.domain.answer.service;

import com.example.lovedocumentbackend.domain.answer.dto.response.AnswerResponse;
import com.example.lovedocumentbackend.domain.answer.entity.*;
import com.example.lovedocumentbackend.domain.answer.repository.*;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemExampleRepository;
import com.example.lovedocumentbackend.domain.ideal.entity.ChoiceResultExample;
import com.example.lovedocumentbackend.domain.ideal.entity.ScoreResultExample;
import com.example.lovedocumentbackend.domain.ideal.entity.YnResultExample;
import com.example.lovedocumentbackend.domain.ideal.repository.ChoiceResultExampleRepository;
import com.example.lovedocumentbackend.domain.ideal.repository.ScoreResultExampleRepository;
import com.example.lovedocumentbackend.domain.ideal.repository.YnResultExampleRepository;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerResult {
    private final AnswerYnRepository answerYnRepository;
    private final AnswerScoreRepository answerScoreRepository;
    private final AnswerNumberRepository answerNumberRepository;
    private final AnswerChoiceRepository answerChoiceRepository;
    private final ChoiceResultExampleRepository choiceResultExampleRepository;
    private final ScoreResultExampleRepository scoreResultExampleRepository;
    private final YnResultExampleRepository ynResultExampleRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;
    private final CategoryItemAnswerResultRepository categoryItemAnswerResultRepository;

    public AnswerResponse.AnswerInfo makeAnswerResult(Answer answer, CategoryItem categoryItem){
        CategoryItemAnswerResult categoryItemAnswerResult = categoryItemAnswerResultRepository.findByCategoryItem(categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

        if (categoryItem.getType() == QuestionType.RANGE) {
            AnswerNumber answerNumber = answerNumberRepository.findByAnswerAndCategoryItem(answer,categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_ANSWER));

            return responseTo(categoryItemAnswerResult, answerNumber.getNumber().toString());
        } else if (categoryItem.getType() == QuestionType.CHOICE) {
            AnswerChoice answerChoice = answerChoiceRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_ANSWER));
            ChoiceResultExample choiceResultExample = choiceResultExampleRepository.findByCategoryItemExample(answerChoice.getCategoryItemExample()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_ANSWER));
            return responseTo(categoryItemAnswerResult, choiceResultExample.getAnswerResult());
        } else if (categoryItem.getType() == QuestionType.SCORE) {
            AnswerScore answerScore = answerScoreRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_ANSWER));
            ScoreResultExample scoreResultExample = scoreResultExampleRepository.findByAnswerAndCategoryItem(answerScore.getScore(),categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

            return responseTo(categoryItemAnswerResult, scoreResultExample.getAnswerResult());
        }else if (categoryItem.getType() == QuestionType.YN) {
            AnswerYn answerYn = answerYnRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_ANSWER));
            YnResultExample ynResultExample = ynResultExampleRepository.findByAnswerAndCategoryItem(answerYn.getContent(), answerYn.getCategoryItem()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

            return responseTo(categoryItemAnswerResult, ynResultExample.getAnswerResult());
        }else{
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    public AnswerResponse.AnswerInfo responseTo(CategoryItemAnswerResult result, String ans){
        return AnswerResponse.AnswerInfo.builder()
                .start(result.getStart())
                .mid(result.getMid())
                .end(result.getEnd())
                .location(result.getLocation())
                .answer(ans)
                .build();
    }
}
