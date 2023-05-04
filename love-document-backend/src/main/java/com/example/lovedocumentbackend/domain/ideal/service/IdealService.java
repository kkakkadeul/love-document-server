package com.example.lovedocumentbackend.domain.ideal.service;

import com.example.lovedocumentbackend.domain.category.entity.Category;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.ideal.dto.request.IdealRequest;
import com.example.lovedocumentbackend.domain.ideal.dto.response.IdealResponse;
import com.example.lovedocumentbackend.domain.ideal.entity.*;
import com.example.lovedocumentbackend.domain.ideal.repository.*;
import com.example.lovedocumentbackend.domain.question.entity.Question;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.domain.question.repository.QuestionRepository;
import com.example.lovedocumentbackend.domain.user.entity.User;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemExampleRepository;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemRepository;
import com.example.lovedocumentbackend.domain.question.repository.QuestionGroupRepository;
import com.example.lovedocumentbackend.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class IdealService {

    private final IdealRepository idealRepository;
    private final IdealYnRepository idealYnRepository;
    private final IdealScoreRepository idealScoreRepository;
    private final IdealRangeRepository idealRangeRepository;
    private final IdealChoiceRepository idealChoiceRepository;
    private final UserRepository userRepository;
    private final QuestionGroupRepository questionGroupRepository;
    private final QuestionRepository questionRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;

    private final IdealResult idealResult;

    // 유저 답안 불러오기
    public List<IdealResponse> userIdeal(String nickname){
        // 유저의 questionGroup 찾기
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup userQuestionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        Optional<Ideal> idealOptional = idealRepository.findByQuestionGroupIdAndStatus(userQuestionGroup.getId(), BooleanType.Y);
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(userQuestionGroup.getId());

        List<CategoryItem> categoryItemList = new ArrayList<>();
        Set<Category> categoryList = questionList.stream()
                .map(Question::getCategory)
                .collect(Collectors.toSet());


        questionList.forEach(question -> {
            categoryItemList.add(categoryItemRepository.findById(question.getCategoryItemId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM)));
        });

        List<IdealResponse> idealList;
        if (idealOptional.isPresent()){
            idealList = new ArrayList<>();
            idealOptional.ifPresent(ideal -> {
                categoryList.forEach(category -> {
                    List<IdealResponse.IdealInfo> idealInfoList = new ArrayList<>();

                    categoryItemList.forEach(categoryItem -> {
                        if(Objects.equals(categoryItem.getCategory().getTitle(), category.getTitle())){
                            IdealResponse.IdealInfo idealInfo = IdealResponse.IdealInfo.builder()
                                    .categoryTitle(categoryItem.getTitle())
                                    .ideal(idealResult.makeIdealResult(ideal, categoryItem))
                                    .build();
                            idealInfoList.add(idealInfo);
                        }
                    });

                    IdealResponse idealResponse = IdealResponse.builder()
                            .emoji(category.getEmoji())
                            .categoryTitle(category.getTitle())
                            .idealInfoList(idealInfoList)
                            .build();

                    idealList.add(idealResponse);
                });
            });
        } else{
            idealList = null;
        }

        return idealList;
    }

    // 유저 답안 저장 로직

    @Transactional
    public void saveIdeal(String nickname, IdealRequest request){
        // 유저의 QuestionGroup 찾기
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup userQuestionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        Optional<Ideal> optional = idealRepository.findByQuestionGroupIdAndStatus(userQuestionGroup.getId(), BooleanType.Y);

        optional.ifPresentOrElse(
                beforeIdeal -> {
                    // 기존 ideal을 update
                    beforeIdeal.setStatus(BooleanType.N);
                    idealRepository.save(beforeIdeal);

                    Ideal newIdeal = Ideal.builder()
                            .questionGroup(userQuestionGroup)
                            .status(BooleanType.Y)
                            .build();
                    idealRepository.save(newIdeal);
                },
                () -> {
                    // ideal을 새로 생성
                    Ideal newIdeal = Ideal.builder()
                            .questionGroup(userQuestionGroup)
                            .status(BooleanType.Y)
                            .build();
                    idealRepository.save(newIdeal);
                }
        );

        Ideal userIdeal = idealRepository.findByQuestionGroupIdAndStatus(userQuestionGroup.getId(), BooleanType.Y)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_IDEAL));

        // 각 ideal 테이블의 위에서 만든 ideal 매핑해서 넣어줌
        request.getIdealList().forEach(ideal -> {
            CategoryItem categoryItem = categoryItemRepository.findById(ideal.getCategoryItemId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM));
            QuestionType questionType = ideal.getQuestionType();

            if (questionType == QuestionType.RANGE){
                if (ideal.getRangeList().size() == 0){
                    throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_IDEAL);
                }

                IdealRange idealRange = IdealRange.builder()
                        .more(ideal.getRangeList().isEmpty() ? null : ideal.getRangeList().get(0))
                        .less(ideal.getRangeList().isEmpty() ? null : ideal.getRangeList().get(1))
                        .categoryItem(categoryItem)
                        .ideal(userIdeal)
                        .build();
                idealRangeRepository.save(idealRange);
            } else if (questionType == QuestionType.YN) {
                if (ideal.getYn() == null){
                    throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_IDEAL);
                }

                IdealYn idealYn = IdealYn.builder()
                        .content(ideal.getYn())
                        .ideal(userIdeal)
                        .categoryItem(categoryItem)
                        .build();
                idealYnRepository.save(idealYn);

            } else if (questionType == QuestionType.SCORE) {
                if (ideal.getScore() == null){
                    throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_IDEAL);
                }

                IdealScore idealScore = IdealScore.builder()
                        .score(ideal.getScore())
                        .ideal(userIdeal)
                        .categoryItem(categoryItem)
                        .build();
                idealScoreRepository.save(idealScore);

            } else if (questionType == QuestionType.CHOICE) {
                if (ideal.getChoiceIdList().size() == 0){
                    throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_IDEAL);
                }

                ideal.getChoiceIdList().forEach(categoryItemExampleId -> {
                    CategoryItemExample categoryItemExample = categoryItemExampleRepository.findById(categoryItemExampleId).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM_EXAMPLE));

                    IdealChoice idealChoice = IdealChoice.builder()
                            .ideal(userIdeal)
                            .categoryItem(categoryItem)
                            .categoryItemExample(categoryItemExample)
                            .build();

                    idealChoiceRepository.save(idealChoice);
                });

            }
        });

    }

}
