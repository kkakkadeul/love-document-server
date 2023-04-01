package com.example.lovedocumentbackend.service;

import com.example.lovedocumentbackend.dto.request.QuestionApiRequest;
import com.example.lovedocumentbackend.dto.response.QuestionApiResponse;
import com.example.lovedocumentbackend.entity.*;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionApiLogicService {

    private final QuestionRepository questionRepository;

    private final QuestionGroupRepository questionGroupRepository;

    private final UserRepository userRepository;

    private final CategoryItemRepository categoryItemRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryItemExampleRepository categoryItemExampleRepository;

    // 회원가입시 질문지 생성
    // 생성은 한번만 가능함
    public Boolean makeQuestions(String nickName ,List<QuestionApiRequest> requestList) {
        User user = userRepository.findByNickname(nickName);

        if (user != null) {
            throw new RestApiException(CommonErrorCode.AlREADY_REQUEST);
        }

        List<QuestionGroup> optionalList = questionGroupRepository.findAllByUserId(user.getId());

        if (optionalList == null){
            throw new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION);
        }

        // 생성시 한번, post 중복요청 예외처리
        optionalList.forEach(questionG -> {
            if (questionG.getStatus() == BooleanType.Y){
                throw new RestApiException(CommonErrorCode.NOT_FOUND);
            }
        });

        QuestionGroup questionGroup = QuestionGroup.builder()
                .user(user)
                .itemNum(requestList.size())
                .status(BooleanType.Y)
                .build();

        QuestionGroup savedQuestionGroup = questionGroupRepository.save(questionGroup);

        String link = "/research/"+savedQuestionGroup.getId();

        savedQuestionGroup.setLink(link);
        QuestionGroup newSavedQuestionGroup = questionGroupRepository.save(savedQuestionGroup);

        requestList.forEach(request -> {
            CategoryItem categoryItem = null;
            try {
                categoryItem = categoryItemRepository.findById(request.getId()).orElseThrow(Exception::new);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Question question = Question.builder()
                    .questionGroup(savedQuestionGroup)
                    .categoryItem(categoryItem)
                    .build();

            questionRepository.save(question);
        });
        return true;
    }

    public List<QuestionApiResponse> getIdeal(String nickname) {

        User user = userRepository.findByNickname(nickname);
        QuestionGroup questionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y);
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(questionGroup.getId());
        Set<Category> categoryList = new HashSet<>();

        if (user == null) {
            throw new RestApiException(CommonErrorCode.NOT_FOUND_USER);
        } else if (questionGroup == null) {
            throw new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION);
        } else if (questionList == null) {
            throw new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION);
        }


        questionList.forEach(question -> {
            try {
                CategoryItem categoryItem = categoryItemRepository.findById(question.getCategoryItem().getId()).orElseThrow(Exception::new);
                Category category = categoryRepository.findById(categoryItem.getCategory().getId()).orElseThrow(Exception::new);

                categoryList.add(category);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return reponse(questionList, categoryList);
    }

    private List<QuestionApiResponse> reponse(List<Question> questionList, Set<Category> categoryList) {
        List<QuestionApiResponse> questionApiResponseList = new ArrayList<>();

        categoryList.forEach(category -> {
            List<QuestionApiResponse.CategoryItemInfo> categoryItemInfoList = new ArrayList<>();

            questionList.forEach(question -> {
                if (Objects.equals(category.getId(), question.getCategoryItem().getCategory().getId())){
                    List<String> contents = new ArrayList<>();
                    List<CategoryItemExample> categoryItemExampleList = categoryItemExampleRepository.findAllByCategoryItemId(question.getCategoryItem().getId());

                    categoryItemExampleList.forEach(categoryItemExample -> {
                        contents.add(categoryItemExample.getContent());
                    });

                    QuestionApiResponse.CategoryItemInfo categoryItemInfo = QuestionApiResponse.CategoryItemInfo.builder()
                            .id(question.getCategoryItem().getId())
                            .multiple(question.getCategoryItem().getMultiple())
                            .type(question.getCategoryItem().getType())
                            .question(question.getCategoryItem().getIdealQuestion())
                            .contentList(contents)
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
