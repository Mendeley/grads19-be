package com.gradproject2019;

import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUtils {
    @Autowired
    public AuthRepository authRepository;

    @Autowired
    public UserRepository userRepository;

    public void clearRepositories() {
        authRepository.deleteAll();
        userRepository.deleteAll();
    }
}
