package com.example.lovedocumentbackend.service;

import com.example.lovedocumentbackend.dto.request.QuestionApiRequest;
import com.example.lovedocumentbackend.dto.response.QuestionApiResponse;
import com.example.lovedocumentbackend.entity.*;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.ideal.entity.*;
import com.example.lovedocumentbackend.ideal.repository.*;
import com.example.lovedocumentbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionApiLogicService {

    private final QuestionRepository questionRepository;
    private final QuestionGroupRepository questionGroupRepository;
    private final UserRepository userRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;
    private final IdealRepository idealRepository;
    private final IdealChoiceRepository idealChoiceRepository;
    private final IdealRangeRepository idealRangeRepository;
    private final IdealScoreRepository idealScoreRepository;
    private final IdealYnRepository idealYnRepository;

    // 회원가입시 질문지 생성
    // 생성은 한번만 가능함
    @Transactional
    public void firstMakeQuestions(String nickName ,QuestionApiRequest request) {
        User user = userRepository.findByNickname(nickName).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));

        QuestionGroup questionGroup = QuestionGroup.builder()
                .user(user)
                .itemNum(request.getCategoryItems().size())
                .status(BooleanType.Y)
                .build();

        QuestionGroup savedQuestionGroup = questionGroupRepository.save(questionGroup);

        String link = "/research/"+savedQuestionGroup.getId();

        savedQuestionGroup.setLink(link);
        QuestionGroup newSavedQuestionGroup = questionGroupRepository.save(savedQuestionGroup);

        request.getCategoryItems().forEach(id -> {
            CategoryItem categoryItem = categoryItemRepository.findById(id)
                    .orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

            Question question = Question.builder()
                    .questionGroup(newSavedQuestionGroup)
                    .categoryItem(categoryItem)
                    .build();

            questionRepository.save(question);
        });
    }

    public List<QuestionApiResponse> getIdeal(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup questionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        Optional<Ideal> ideal = idealRepository.findByQuestionGroupId(questionGroup.getId());
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(questionGroup.getId());
        Set<Category> categoryList = questionList.stream()
                .map(Question::getCategory)
                .collect(Collectors.toSet());

        return toResponse(questionList, categoryList,ideal);
    }

    private List<QuestionApiResponse> toResponse(List<Question> questionList, Set<Category> categoryList, Optional<Ideal> ideal) {
        List<QuestionApiResponse> questionApiResponseList = new ArrayList<>();

        categoryList.forEach(category -> {
            List<QuestionApiResponse.CategoryItemInfo> categoryItemInfoList = new ArrayList<>();

            questionList.forEach(question -> {
                if (Objects.equals(category.getId(), question.getCategoryId())){
                    List<CategoryItemExample> categoryItemExampleList = categoryItemExampleRepository.findAllByCategoryItemId(question.getCategoryItemId());

                    List<QuestionApiResponse.Example> contents = categoryItemExampleList.stream()
                            .map(CategoryItemExample -> {
                                return QuestionApiResponse.Example.builder()
                                        .id(CategoryItemExample.getId())
                                        .content(CategoryItemExample.getContent())
                                        .build();
                            })
                            .collect(Collectors.toList());

                    List<Long> choiceIdList = new ArrayList<>();
                    List<Integer> rangeNumList = new ArrayList<>();
                    List<Integer> scoreNumList = new ArrayList<>();
                    List<BooleanType> ynBoolList = new ArrayList<>();

                    ideal.ifPresent(userIdeal -> {
                        if (question.getCategoryItem().getType() == QuestionType.YN){
                            Optional<IdealYn> idealYn = idealYnRepository.findByIdealAndCategoryItem(userIdeal, question.getCategoryItem());
                            idealYn.ifPresent(yn -> {
                                ynBoolList.add(yn.getContent());
                            });

                        } else if (question.getCategoryItem().getType() == QuestionType.RANGE) {
                            Optional<IdealRange> idealRange = idealRangeRepository.findByIdealAndCategoryItem(userIdeal, question.getCategoryItem());
                            idealRange.ifPresent(range -> {
                                rangeNumList.add(range.getMore());
                                rangeNumList.add(range.getLess());
                            });

                        } else if (question.getCategoryItem().getType() == QuestionType.SCORE) {
                            Optional<IdealScore> idealScore = idealScoreRepository.findByIdealAndCategoryItem(userIdeal, question.getCategoryItem());
                            idealScore.ifPresent(score -> {
                                scoreNumList.add(score.getScore());
                            });

                        } else if (question.getCategoryItem().getType() == QuestionType.CHOICE) {
                            categoryItemExampleList.forEach(ex -> {
                                Optional<IdealChoice> idealChoice = idealChoiceRepository.findByIdealAndCategoryItemExample(userIdeal, ex);
                                idealChoice.ifPresent(choice -> {
                                    choiceIdList.add(choice.getId());
                                });
                            });
                        }
                    });


                    QuestionApiResponse.CategoryItemInfo categoryItemInfo = QuestionApiResponse.CategoryItemInfo.builder()
                            .id(question.getCategoryItem().getId())
                            .multiple(question.getCategoryItem().getIdealMultiple())
                            .type(question.getCategoryItem().getType())
                            .question(question.getCategoryItem().getIdealQuestion())
                            .negativeLabel(question.getCategoryItem().getIdealNegativeLabel())
                            .positiveLabel(question.getCategoryItem().getIdealPositiveLabel())
                            .choiceIdList(choiceIdList)
                            .rangeNumList(rangeNumList)
                            .scoreNumList(scoreNumList)
                            .ynBoolList(ynBoolList)
                            .exampleList(contents)
                            .build();

                    categoryItemInfoList.add(categoryItemInfo);
                }
            });

            QuestionApiResponse questionApiResponse = QuestionApiResponse.builder()
                    .categoryTitle(category.getTitle())
                    .categoryItemInfoList(categoryItemInfoList)
                    .build();

            questionApiResponseList.add(questionApiResponse);
        });

        return questionApiResponseList;
    }


}
