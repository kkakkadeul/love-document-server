package com.example.lovedocumentbackend.domain.answer.service;

import com.example.lovedocumentbackend.domain.answer.dto.request.AnswerRequest;
import com.example.lovedocumentbackend.domain.answer.entity.*;
import com.example.lovedocumentbackend.domain.answer.repository.*;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemExampleRepository;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemRepository;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.domain.question.repository.QuestionGroupRepository;
import com.example.lovedocumentbackend.domain.user.service.MakePercentage;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final QuestionGroupRepository questionGroupRepository;
    private final AnswerRepository answerRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final AnswerNumberRepository answerNumberRepository;
    private final AnswerYnRepository answerYnRepository;

    private final AnswerChoiceRepository answerChoiceRepository;
    private final AnswerScoreRepository answerScoreRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;

    @Transactional
    public void saveAnswer(AnswerRequest request){
        QuestionGroup userQuestionGroup = questionGroupRepository.findById(request.getQuestionId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        Answer newAnswer = Answer.builder()
                .questionGroup(userQuestionGroup)
                .userShow(BooleanType.N)
                .nickname(request.getNickname())
                .age(request.getAge())
                .work(request.getWork())
                .live(request.getLive())
                .build();

        Answer savedAnswer = answerRepository.save(newAnswer);

        request.getAnswerList().forEach(answer -> {
            CategoryItem categoryItem = categoryItemRepository.findById(answer.getCategoryItemId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM));
            QuestionType questionType = answer.getQuestionType();

            if (questionType == QuestionType.NUMBER) {
                if(answer.getNumber() == null) throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_ANSWER);

                AnswerNumber answerNumber = AnswerNumber.builder()
                        .number(answer.getNumber())
                        .categoryItem(categoryItem)
                        .answer(savedAnswer)
                        .build();
                answerNumberRepository.save(answerNumber);
            }else if (questionType == QuestionType.YN){
                if(answer.getYn() == null) throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_ANSWER);

                AnswerYn answerYn = AnswerYn.builder()
                        .content(answer.getYn())
                        .answer(savedAnswer)
                        .categoryItem(categoryItem)
                        .build();
                answerYnRepository.save(answerYn);
            }else if(questionType == QuestionType.SCORE){
                if(answer.getScore() == null) throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_ANSWER);

                AnswerScore answerScore = AnswerScore.builder()
                        .score(answer.getScore())
                        .answer(savedAnswer)
                        .categoryItem(categoryItem)
                        .build();
                answerScoreRepository.save(answerScore);
            }else if (questionType == QuestionType.CHOICE){
                if(answer.getChoiceId()==null) throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_ANSWER);

                CategoryItemExample categoryItemExample = categoryItemExampleRepository.findById(answer.getChoiceId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM_EXAMPLE));

                AnswerChoice answerChoice = AnswerChoice.builder()
                            .answer(savedAnswer)
                            .categoryItem(categoryItem)
                            .categoryItemExample(categoryItemExample)
                            .build();

                answerChoiceRepository.save(answerChoice);
            } else{
                throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_ANSWER);
            }

        });
    }
}
