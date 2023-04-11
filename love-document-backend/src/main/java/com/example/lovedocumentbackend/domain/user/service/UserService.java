package com.example.lovedocumentbackend.domain.user.service;

import com.example.lovedocumentbackend.auth.JwtProvider;
import com.example.lovedocumentbackend.domain.user.repository.UserRepository;
import com.example.lovedocumentbackend.domain.user.dto.request.UserRequest;
import com.example.lovedocumentbackend.domain.user.dto.response.UserResponse;
import com.example.lovedocumentbackend.domain.user.entity.User;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

   @Transactional
    public UserResponse login(UserRequest request) {
        User user = userRepository.findByNickname(request.getNickname()).orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_USER));;

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

    private UserResponse response(User user){
        return UserResponse.builder()
                .nickname(user.getNickname())
                .token(jwtProvider.createToken(user.getNickname()))
                .build();
    }

}
