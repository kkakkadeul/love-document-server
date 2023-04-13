package com.example.lovedocumentbackend.domain.user.service;
import com.example.lovedocumentbackend.auth.JwtProvider;
import com.example.lovedocumentbackend.domain.user.dto.request.UserRequest;
import com.example.lovedocumentbackend.domain.user.dto.response.UserResponse;
import com.example.lovedocumentbackend.domain.user.entity.User;
import com.example.lovedocumentbackend.domain.user.repository.UserRepository;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@DisplayName("UserService 테스트")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserService userService;

    private UserRequest validRequest;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validRequest = UserRequest.builder()
                .nickname("testuser")
                .password("testpassword")
                .build();
    }

    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() {
        // Given
        String encodedPassword = "encodedpassword";
        User user = User.builder()
                .nickname(validRequest.getNickname())
                .password(encodedPassword)
                .build();
        Mockito.when(userRepository.findByNickname(validRequest.getNickname())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(validRequest.getPassword(), encodedPassword)).thenReturn(true);
        Mockito.when(jwtProvider.createToken(validRequest.getNickname())).thenReturn("testtoken");

        // When
        UserResponse response = userService.login(validRequest);

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(validRequest.getNickname(), response.getNickname());
        Assertions.assertEquals("testtoken", response.getToken());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    @DisplayName("로그인 실패 - 유저 정보 없음")
    void testLoginFailNotFoundUser() {
        // Given
        Mockito.when(userRepository.findByNickname(validRequest.getNickname())).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(RestApiException.class, () -> userService.login(validRequest));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 오류")
    void testLoginFailPasswordError() {
        // Given
        String encodedPassword = "encodedpassword";
        User user = User.builder()
                .nickname(validRequest.getNickname())
                .password(encodedPassword)
                .build();
        Mockito.when(userRepository.findByNickname(validRequest.getNickname())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(validRequest.getPassword(), encodedPassword)).thenReturn(false);

        // When & Then
        Assertions.assertThrows(RestApiException.class, () -> userService.login(validRequest));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("회원가입 성공")
    public void testRegisterSuccess() {
        // given
        UserRequest userRequest = UserRequest.builder()
                .nickname("testuser")
                .password("password123")
                .build();

        Mockito.when(userRepository.findByNickname(userRequest.getNickname())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encrypted_password");
        Mockito.when(jwtProvider.createToken(userRequest.getNickname())).thenReturn("test_token");

        // when
        UserResponse userResponse = userService.register(userRequest);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(userRequest.getNickname(), savedUser.getNickname());
        Assertions.assertEquals("encrypted_password", savedUser.getPassword());

        Assertions.assertEquals(userRequest.getNickname(), userResponse.getNickname());
        Assertions.assertEquals("test_token", userResponse.getToken());
    }

    @Test
    @DisplayName("회원가입 실패")
    public void testRegisterFailureUserExists() {
        // given
        UserRequest userRequest = UserRequest.builder()
                .nickname("testuser")
                .password("password123")
                .build();

        User existingUser = User.builder()
                .nickname(userRequest.getNickname())
                .password("existing_encrypted_password")
                .build();

        Mockito.when(userRepository.findByNickname(userRequest.getNickname())).thenReturn(Optional.of(existingUser));

        // when
        RestApiException exception = Assertions.assertThrows(RestApiException.class, () -> userService.register(userRequest));

        // then
        Assertions.assertEquals(CommonErrorCode.AlREADY_USER, exception.getErrorCode());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }


}
