package com.example.lovedocumentbackend.domain.answer.service;

import com.example.lovedocumentbackend.domain.answer.dto.response.AnswerResponse;
import com.example.lovedocumentbackend.domain.answer.entity.*;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerChoiceRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerNumberRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerScoreRepository;
import com.example.lovedocumentbackend.domain.answer.repository.AnswerYnRepository;
import com.example.lovedocumentbackend.domain.category.entity.Category;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.*;
import com.example.lovedocumentbackend.domain.ideal.repository.*;
import com.example.lovedocumentbackend.domain.ideal.service.IdealResult;
import com.example.lovedocumentbackend.domain.question.entity.Question;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.domain.question.repository.QuestionRepository;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import com.example.lovedocumentbackend.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MakeAnswerResponse {
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
    private final AnswerResult answerResult;
    private final IdealResult idealResult;

    public AnswerResponse getAnswerResponse(QuestionGroup questionGroup, Answer answer){
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(questionGroup.getId());
        Optional<Ideal> optional = idealRepository.findByQuestionGroupId(questionGroup.getId());
        Set<Category> categoryList = questionList.stream()
                .map(Question::getCategory)
                .collect(Collectors.toSet());

        if(optional.isEmpty()) {
            AnswerResponse answerResponse = new AnswerResponse();

            answerResponse.setAge(answer.getAge());
            answerResponse.setNickname(answer.getNickname());
            answerResponse.setLive(answer.getLive());
            answerResponse.setWork(answer.getWork());
            answerResponse.setDateTime(answer.getCreateAt());

            answerResponse.setPercentage(0);
            answerResponse.setTotalCnt(questionList.size());
            answerResponse.setMatchCnt(0);

            List<AnswerResponse.CategoryInfo> categoryInfoList = new ArrayList<>();
            Set<String> nonMatchList = new HashSet<>();

            categoryList.forEach(category -> {
                List<AnswerResponse.Item> itemList = new ArrayList<>();

                for (Question question : questionList) {
                    if(Objects.equals(category.getId(), question.getCategoryId())){
                        CategoryItem categoryItem = question.getCategoryItem();

                        itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(null)
                                .build());

                        nonMatchList.add(categoryItem.getTitle());
                    }
                }
                categoryInfoList.add(AnswerResponse.CategoryInfo.builder()
                                .title(category.getTitle())
                                .itemList(itemList)
                        .build());
            });

            answerResponse.setNonMatchTitleList(nonMatchList);
            answerResponse.setCategoryInfoList(categoryInfoList);

            return answerResponse;
        } else {
            Ideal ideal = optional.get();
            AnswerResponse answerResponse = new AnswerResponse();

            List<AnswerResponse.CategoryInfo> categoryInfoList = new ArrayList<>();
            Set<String> nonMatchList = new HashSet<>();
            List<Integer> intList = new ArrayList<>();

            answerResponse.setAge(answer.getAge());
            answerResponse.setNickname(answer.getNickname());
            answerResponse.setLive(answer.getLive());
            answerResponse.setWork(answer.getWork());
            answerResponse.setDateTime(answer.getCreateAt());

            categoryList.forEach(category -> {
                List<AnswerResponse.Item> itemList = new ArrayList<>();

                for (Question question : questionList) {
                    if(Objects.equals(category.getId(), question.getCategoryId())){
                        CategoryItem categoryItem = question.getCategoryItem();

                        // ================
                        if (categoryItem.getType() == QuestionType.YN) {
                            IdealYn idealYn = idealYnRepository.findByIdealAndCategoryItem(ideal,categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            AnswerYn answerYn = answerYnRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            if(idealYn.getContent() == answerYn.getContent()) {
                                intList.add(0);
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(null)
                                        .build());
                            }else{
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(idealResult.makeIdealResult(ideal, categoryItem))
                                        .build());
                                nonMatchList.add(categoryItem.getTitle());
                            }

                        } else if (categoryItem.getType() == QuestionType.SCORE) {
                            IdealScore idealScore = idealScoreRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            AnswerScore answerScore = answerScoreRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            if(Objects.equals(idealScore.getScore(), answerScore.getScore())) {
                                intList.add(0);
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(null)
                                        .build());
                            }else{
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(idealResult.makeIdealResult(ideal, categoryItem))
                                        .build());
                                nonMatchList.add(categoryItem.getTitle());
                            }
                        } else if (categoryItem.getType() == QuestionType.CHOICE) {
                            List<IdealChoice> idealChoiceList = idealChoiceRepository.findByIdealAndCategoryItem(ideal, categoryItem);
                            AnswerChoice answerChoice = answerChoiceRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            List<Integer> checkList = new ArrayList<>();
                            idealChoiceList.forEach(idealChoice -> {
                                if (idealChoice.getCategoryItemExample()==answerChoice.getCategoryItemExample()){
                                    intList.add(0);
                                    checkList.add(1);
                                }
                            });

                            if(checkList.size()==1){
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(null)
                                        .build());
                            }else{
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(idealResult.makeIdealResult(ideal, categoryItem))
                                        .build());
                                nonMatchList.add(categoryItem.getTitle());
                            }

                        } else if (categoryItem.getType() == QuestionType.RANGE) {
                            IdealRange idealRange = idealRangeRepository.findByIdealAndCategoryItem(ideal, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            AnswerNumber answerNumber = answerNumberRepository.findByAnswerAndCategoryItem(answer, categoryItem).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND));
                            if(idealRange.getMore()<=answerNumber.getNumber() && answerNumber.getNumber()<=idealRange.getLess()){
                                intList.add(0);
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(null)
                                        .build());
                            }else{
                                itemList.add(AnswerResponse.Item.builder()
                                        .answerInfo(answerResult.makeAnswerResult(answer, categoryItem))
                                        .ideal(idealResult.makeIdealResult(ideal, categoryItem))
                                        .build());
                                nonMatchList.add(categoryItem.getTitle());
                            }
                        }
                        // ================
                    }
                }
                categoryInfoList.add(AnswerResponse.CategoryInfo.builder()
                        .title(category.getTitle())
                        .itemList(itemList)
                        .build());
            });

            answerResponse.setPercentage((int)Math.round(((double)intList.size()/(double)questionList.size()*100.0)));
            answerResponse.setMatchCnt(intList.size());
            answerResponse.setTotalCnt(questionList.size());
            answerResponse.setNonMatchTitleList(nonMatchList);
            answerResponse.setCategoryInfoList(categoryInfoList);

            return answerResponse;
        }
    }
}
