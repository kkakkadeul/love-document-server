package com.example.lovedocumentbackend.auth;

import com.example.lovedocumentbackend.domain.user.entity.User;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByNickname(username).orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_USER));

        return new CustomUserDetails(user);
    }
}
