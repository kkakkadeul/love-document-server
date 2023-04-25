package com.example.lovedocumentbackend.domain.ideal.service;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemExampleRepository;
import com.example.lovedocumentbackend.domain.ideal.entity.*;
import com.example.lovedocumentbackend.domain.ideal.repository.*;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IdealResult {
    private final IdealYnRepository idealYnRepository;
    private final IdealScoreRepository idealScoreRepository;
    private final IdealRangeRepository idealRangeRepository;
    private final IdealChoiceRepository idealChoiceRepository;
    private final CategoryItemIdealResultRepository categoryItemIdealResultRepository;
    private final CategoryItemExampleRepository categoryItemExampleRepository;
    private final ChoiceResultExampleRepository choiceResultExampleRepository;
    private final ScoreResultExampleRepository scoreResultExampleRepository;
    private final YnResultExampleRepository ynResultExampleRepository;

    public String makeIdealResult(Ideal ideal, CategoryItem categoryItem){
        CategoryItemIdealResult result = categoryItemIdealResultRepository.findByCategoryItem(categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

        if (categoryItem.getType() == QuestionType.RANGE){
            IdealRange idealRange = idealRangeRepository.findByIdealAndCategoryItem(ideal,categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_IDEAL));

            return makeRangeString(result.getStart(), result.getMid(), result.getEnd(), idealRange.getMore(), idealRange.getLess());

        } else if (categoryItem.getType() == QuestionType.CHOICE) {
            List<CategoryItemExample> categoryItemExampleList = categoryItemExampleRepository.findAllByCategoryItemId(categoryItem.getId());
            List<String> choiceList = new ArrayList<>();

            categoryItemExampleList.forEach(ex -> {
                Optional<IdealChoice> idealChoice = idealChoiceRepository.findByIdealAndCategoryItemExample(ideal, ex);

                idealChoice.ifPresent(choice -> {
                    choiceList.add(choiceResultExampleRepository.findByCategoryItemExample(ex).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND)).getIdealResult());
                });
            });

            return makeChoiceString(result.getStart(), result.getMid(), result.getEnd(), result.getLocation(), choiceList);
        } else if (categoryItem.getType() == QuestionType.SCORE) {
            IdealScore idealScore = idealScoreRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));
            ScoreResultExample scoreResultExample = scoreResultExampleRepository.findByAnswerAndCategoryItem(idealScore.getScore(),categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

            return  makeString(result, scoreResultExample.getIdealResult());

        } else if (categoryItem.getType() == QuestionType.YN) {
            IdealYn idealYn = idealYnRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));
            YnResultExample ynResultExample = ynResultExampleRepository.findByAnswerAndCategoryItem(idealYn.getContent(), idealYn.getCategoryItem()).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND));

            return makeString(result, ynResultExample.getIdealResult());

        } else{
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
    public String makeString(CategoryItemIdealResult result, String idealResult){
        StringBuilder answer = new StringBuilder();

        if (result.getLocation() == 1){
            answer.append(idealResult);
            if(result.getStart() != null) answer.append(result.getStart());
            if(result.getMid() != null) answer.append(result.getMid());
            if(result.getEnd() != null) answer.append(result.getEnd());

        } else if (result.getLocation() == 2) {
            if(result.getStart() != null) answer.append(result.getStart());
            answer.append(idealResult);
            if(result.getMid() != null) answer.append(result.getMid());
            if(result.getEnd() != null) answer.append(result.getEnd());

        } else if (result.getLocation() == 3) {
            if(result.getStart() != null) answer.append(result.getStart());
            if(result.getMid() != null) answer.append(result.getMid());
            answer.append(idealResult);
            if(result.getEnd() != null) answer.append(result.getEnd());

        }

        return answer.toString();
    }

    public String makeRangeString(String start, String mid, String end, Integer more, Integer less){
        StringBuilder answer = new StringBuilder();

            answer.append(more.toString());
            answer.append(start);
            answer.append(" ");
            answer.append(less.toString());
            answer.append(mid);
            answer.append(" ");
            answer.append(end);

        return answer.toString();
    }

    public String makeChoiceString(String start, String mid, String end, Integer location, List<String> choiceList){
        StringBuilder answer = new StringBuilder();
        StringBuilder choiceString = new StringBuilder();

        for(int i =0 ;i<choiceList.size();i++){
            if(i == 0){
                choiceString.append(choiceList.get(i));
            }
            else{
                choiceString.append(",");
                choiceString.append(" ");
                choiceString.append(choiceList.get(i));
            }
        }

        if (location == 1){
            answer.append(choiceString);
            if(start != null) answer.append(start);
            if(mid != null) answer.append(mid);
            if(end != null) answer.append(end);

        } else if (location == 2) {
            if(start != null) answer.append(start);
            answer.append(choiceString);
            if(mid != null) answer.append(mid);
            if(end != null) answer.append(end);

        } else if (location == 3) {
            if(start != null) answer.append(start);
            if(mid != null) answer.append(mid);
            answer.append(choiceString);
            if(end != null) answer.append(end);

        }
        return answer.toString();
    }
}
