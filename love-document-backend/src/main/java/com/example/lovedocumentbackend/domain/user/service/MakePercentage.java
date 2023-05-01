package com.example.lovedocumentbackend.domain.user.service;

import com.example.lovedocumentbackend.domain.answer.entity.*;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerChoiceRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerNumberRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerScoreRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerYnRepository;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.*;
import com.example.lovedocumentbackend.domain.ideal.repository.*;
import com.example.lovedocumentbackend.domain.question.entity.Question;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.domain.question.repository.QuestionRepository;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MakePercentage {

    private final QuestionRepository questionRepository;
    private final IdealRepository idealRepository;
    private final IdealYnRepository idealYnRepository;
    private final AnswerYnRepository answerYnRepository;
    private final IdealScoreRepository idealScoreRepository;
    private final AnswerScoreRepository answerScoreRepository;
    private final IdealRangeRepository idealRangeRepository;
    private final AnswerNumberRepository answerNumberRepository;
    private final IdealChoiceRepository idealChoiceRepository;
    private final AnswerChoiceRepository answerChoiceRepository;

    public PercentageInfo getPercentage(QuestionGroup questionGroup, Answer answer){
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(questionGroup.getId());
        Optional<Ideal> optional = idealRepository.findByQuestionGroupId(questionGroup.getId());
        Ideal ideal;
        PercentageInfo percentageInfo = new PercentageInfo();
        percentageInfo.setPercentage(0);
        percentageInfo.setTotalCnt(questionList.size());
        percentageInfo.setMatchCnt(0);
        percentageInfo.setNonMatchCnt(questionList.size());

        if(optional.isEmpty()) return percentageInfo;
        else ideal = optional.get();

        List<Integer> intList = new ArrayList<>();
        for (Question question : questionList) {
            CategoryItem categoryItem = question.getCategoryItem();

            if (categoryItem.getType() == QuestionType.YN) {
                IdealYn idealYn = idealYnRepository.findByIdealAndCategoryItem(ideal,categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                AnswerYn answerYn = answerYnRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                if(idealYn.getContent() == answerYn.getContent()) intList.add(0);

            } else if (categoryItem.getType() == QuestionType.SCORE) {
                IdealScore idealScore = idealScoreRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                AnswerScore answerScore = answerScoreRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                if(Objects.equals(idealScore.getScore(), answerScore.getScore())) intList.add(0);

            } else if (categoryItem.getType() == QuestionType.CHOICE) {
                List<IdealChoice> idealChoiceList = idealChoiceRepository.findByIdealAndCategoryItem(ideal, categoryItem);
                AnswerChoice answerChoice = answerChoiceRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                idealChoiceList.forEach(idealChoice -> {
                    if (idealChoice.getCategoryItemExample()==answerChoice.getCategoryItemExample()){
                        intList.add(0);
                    }
                });

            } else if (categoryItem.getType() == QuestionType.RANGE) {
                IdealRange idealRange = idealRangeRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                AnswerNumber answerNumber = answerNumberRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                if(idealRange.getMore()<=answerNumber.getNumber() && answerNumber.getNumber()<=idealRange.getLess()) intList.add(0);
            }

        }

        percentageInfo.setPercentage((int)Math.round(((double)intList.size()/(double)questionList.size()*100.0)));
        percentageInfo.setMatchCnt(intList.size());
        percentageInfo.setNonMatchCnt(questionList.size()-intList.size());
        percentageInfo.setTotalCnt(questionList.size());

        return percentageInfo;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class PercentageInfo {
        private Integer percentage;
        private Integer totalCnt;
        private Integer matchCnt;
        private Integer nonMatchCnt;
    }
}
