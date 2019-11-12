package com.gradproject2019.auth.service;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.exception.IncorrectPasswordException;
import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.exception.UserNotFoundException;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import com.gradproject2019.utils.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    public AuthServiceImpl(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Token login(LoginDto loginDto) {
       User user = userRepository
               .findByUsername(loginDto.getUsername())
               .orElseThrow(UserNotFoundException::new);
       checkPasswordHashMatch(loginDto.getPassword(), user.getPassword());
       return authRepository.saveAndFlush(createToken(user.getId()));
    }

    private void checkPasswordHashMatch(String password, String hash) {
        if (!PasswordUtils.verifyHash(password, hash)) {
            throw new IncorrectPasswordException();
        }
    }

    private Token createToken(Long userId) {
        return Token.TokenBuilder
                .aToken()
                .withUserId(userId)
                .withToken(UUID.randomUUID().toString())
                .build();
    }
}