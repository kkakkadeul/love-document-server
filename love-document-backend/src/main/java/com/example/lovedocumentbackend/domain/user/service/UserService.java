package com.example.lovedocumentbackend.domain.user.service;

import com.example.lovedocumentbackend.SuccessResponse;
import com.example.lovedocumentbackend.auth.JwtProvider;
import com.example.lovedocumentbackend.domain.category.entity.Category;
import com.example.lovedocumentbackend.domain.question.entity.Question;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import com.example.lovedocumentbackend.domain.question.repository.QuestionGroupRepository;
import com.example.lovedocumentbackend.domain.question.repository.QuestionRepository;
import com.example.lovedocumentbackend.domain.user.dto.request.NicknameCheckRequest;
import com.example.lovedocumentbackend.domain.user.dto.response.UserCategoryResponse;
import com.example.lovedocumentbackend.domain.user.dto.response.UserInfoResponse;
import com.example.lovedocumentbackend.domain.user.repository.UserRepository;
import com.example.lovedocumentbackend.domain.user.dto.request.UserRequest;
import com.example.lovedocumentbackend.domain.user.dto.response.UserResponse;
import com.example.lovedocumentbackend.domain.user.entity.User;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final QuestionGroupRepository questionGroupRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

   @Transactional
    public UserResponse login(UserRequest request) {
        User user = userRepository.findByNickname(request.getNickname()).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RestApiException(CommonErrorCode.PASSWORD_ERR);
        }

        userRepository.save(user);

       return response(user);
    }

    @Transactional
    public UserResponse register(UserRequest request) {
        Optional<User> optional = userRepository.findByNickname(request.getNickname());

        if (optional.isPresent()){
            throw new RestApiException(CommonErrorCode.AlREADY_USER);
        }

        User user = User.builder()
                    .nickname(request.getNickname())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

        userRepository.save(user);

        return response(user);
    }

    public SuccessResponse nicknameCheck(NicknameCheckRequest request){
       Optional<User> user = userRepository.findByNickname(request.getNickname());
       if(user.isPresent()){
           throw new RestApiException(CommonErrorCode.AlREADY_USER);
       }

       return SuccessResponse.builder().message("사용 가능한 닉네임이예요.").build();
    }

    public UserInfoResponse getUserInfo(String nickname){
        User user = userRepository.findByNickname(nickname).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup questionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));

       return UserInfoResponse.builder()
               .nickname(nickname)
               .categoryNum(questionGroup.getItemNum())
               .linkId(questionGroup.getLinkId())
               .build();
    }

    public List<UserCategoryResponse> getUserCategories(String nickname){
       List<String> dummy = new ArrayList<>();
        User user = userRepository.findByNickname(nickname).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));
        QuestionGroup questionGroup = questionGroupRepository.findByUserIdAndStatus(user.getId(), BooleanType.Y).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_QUESTION));
        List<Question> questionList = questionRepository.findAllByQuestionGroupId(questionGroup.getId());
        Set<Category> categoryList = questionList.stream()
                .map(Question::getCategory)
                .collect(Collectors.toSet());
        List<UserCategoryResponse> userCategoryResponseList = new ArrayList<>();

        categoryList.forEach(category -> {
            List<String> categoryItemList = new ArrayList<>();

            questionList.forEach(question -> {
                if(Objects.equals(category.getId(), question.getCategoryId())){
                    categoryItemList.add(question.getCategoryItem().getTitle());
                }
            });

            UserCategoryResponse userCategoryResponse = UserCategoryResponse.builder()
                    .categoryTitle(category.getTitle())
                    .categoryItemList(categoryItemList)
                    .build();

            userCategoryResponseList.add(userCategoryResponse);
        });

        return userCategoryResponseList;
    }

    private UserResponse response(User user){
        String token = jwtProvider.createToken(user.getNickname());

        if (token == null) {
            throw new RestApiException(CommonErrorCode.TOKEN_GENERATION_ERROR);
        }
        return UserResponse.builder()
                .nickname(user.getNickname())
                .token(token)
                .build();
    }

}
