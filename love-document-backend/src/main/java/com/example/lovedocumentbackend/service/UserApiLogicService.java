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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserApiLogicService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

   @Transactional
    public UserApiResponse login(UserApiRequest request) {
        User user = userRepository.findByNickname(request.getNickname()).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));;

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RestApiException(CommonErrorCode.PASSWORD_ERR);
        }

        userRepository.save(user);

       return response(user);
    }

    @Transactional
    public UserApiResponse register(UserApiRequest request) {
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

    private UserApiResponse response(User user){
        return UserApiResponse.builder()
                .nickname(user.getNickname())
                .token(jwtProvider.createToken(user.getNickname()))
                .build();
    }

}
