package com.example.lovedocumentbackend.domain.ideal.service;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.ideal.dto.request.IdealRequest;
import com.example.lovedocumentbackend.domain.ideal.entity.*;
import com.example.lovedocumentbackend.domain.ideal.repository.*;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
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

import java.util.Optional;


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
    private final CategoryItemRepository categoryItemRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;

    // 유저 답안 불러오기
    public void userIdeal(){

        // 유저의 questionGroup 찾기

        // 해당 id로 ideal 찾기

        // 해당 ideal 목록 가져와서 카테고리랑 묶어서 반환

    }

    // 유저 답안 저장 로직
    @Transactional
    public void saveIdeal(String nickname, IdealRequest request){
        // 유저의 QuestionGroup 찾기
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup userQuestionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        Optional<Ideal> optional = idealRepository.findByQuestionGroupId(userQuestionGroup.getId());

        optional.ifPresent(idealRepository::delete);

        // questionGroup id로 ideal 생성
        Ideal newIdeal = Ideal.builder()
                .questionGroup(userQuestionGroup)
                .build();

        Ideal userIdeal = idealRepository.save(newIdeal);

        // 각 ideal 테이블의 위에서 만든 ideal 매핑해서 넣어줌
        request.getIdealList().forEach(ideal -> {
            CategoryItem categoryItem = categoryItemRepository.findById(ideal.getCategoryItemId()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_CATEGORY_ITEM));
            QuestionType questionType = ideal.getQuestionType();

            if (questionType == QuestionType.RANGE){
                if (ideal.getRangeList().size() == 0){
                    throw new RestApiException(CommonErrorCode.INVALID_PARAMETER_IDEAL);
                }

                IdealRange idealRange = IdealRange.builder()
                        .more(ideal.getRangeList().get(0))
                        .less(ideal.getRangeList().get(1))
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
                            .categoryItemExample(categoryItemExample)
                            .build();

                    idealChoiceRepository.save(idealChoice);
                });

            }
        });
    }

}
