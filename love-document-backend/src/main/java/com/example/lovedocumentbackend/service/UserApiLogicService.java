package com.example.lovedocumentbackend.service;

import com.example.lovedocumentbackend.component.JwtProvider;
import com.example.lovedocumentbackend.dto.request.UserApiRequest;
import com.example.lovedocumentbackend.dto.response.UserApiResponse;
import com.example.lovedocumentbackend.entity.User;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserApiLogicService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserApiResponse login(UserApiRequest request) {
        User user = userRepository.findByNickname(request.getNickname());

        if (user == null) {
            throw new RestApiException(CommonErrorCode.NOT_FOUND_USER);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RestApiException(CommonErrorCode.PASSWORD_ERR);
        }

        userRepository.save(user);

       return response(user);
    }

    public UserApiResponse register(UserApiRequest request) throws Exception {
        User alreadyUser = userRepository.findByNickname(request.getNickname());

        if (alreadyUser != null) {
            throw new RestApiException(CommonErrorCode.AlREADY_USER);
        }

        User user = User.builder()
                    .nickname(request.getNickname())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

        userRepository.save(user);

        return response(user);
    }

    private UserApiResponse response(User user){

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .nickname(user.getNickname())
                .token(jwtProvider.createToken(user.getNickname()))
                .build();

        return userApiResponse;
    }

}
