package com.example.lovedocumentbackend.domain.user.repository;


import com.example.lovedocumentbackend.LoveDocumentBackendApplicationTests;
import com.example.lovedocumentbackend.entity.User;
import com.example.lovedocumentbackend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends LoveDocumentBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String nickname = "Test01";
        String password = "1234";
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setNickname(nickname)
                .setPassword(password)
                .setCreatedAt(createdAt);

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);

        userRepository.delete(newUser);
    }

    @Test
    public void read(){
        Optional<User> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());
    }


    @Test
    public void update(){
        User user1 = new User();
        user1.setNickname("Test01")
                .setPassword("1234")
                .setCreatedAt(LocalDateTime.now());
        User newUser = userRepository.save(user1);

        Optional<User> updateUser = userRepository.findById(newUser.getId());
        updateUser.ifPresent(user -> {
            user.setNickname("Test02");
            user.setUpdatedAt(LocalDateTime.now());

            User updatedUser = userRepository.save(user);
            Assertions.assertEquals(updatedUser.getNickname(), "Test02");

            userRepository.delete(updatedUser);
        });
    }


}
