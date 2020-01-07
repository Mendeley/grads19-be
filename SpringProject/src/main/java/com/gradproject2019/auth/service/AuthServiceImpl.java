package com.gradproject2019.auth.service;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.exception.InvalidCredentialsException;
import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.users.persistence.User;
import com.gradproject2019.users.service.UserService;
import com.gradproject2019.utils.AuthUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final UserService userService;

    public AuthServiceImpl(AuthRepository authRepository, @Lazy UserService userService) {
        this.authRepository = authRepository;
        this.userService = userService;
    }

    @Override
    public Token login(LoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getUsername());
        checkPasswordHashMatch(loginDto.getPassword(), user.getPassword());
        return authRepository.saveAndFlush(createToken(user.getId()));
    }

    private void checkPasswordHashMatch(String password, String hash) {
        if (!AuthUtils.verifyHash(password, hash)) {
            throw new InvalidCredentialsException();
        }
    }

    private Token createToken(Long userId) {
        return Token.TokenBuilder
                .aToken()
                .withUserId(userId)
                .withToken(UUID.randomUUID())
                .build();
    }

    @Override
    public void logout(UUID token) {
        checkTokenExists(token);
        authRepository.deleteById(token);
    }

    public void checkTokenExists(UUID token) {
        if (!authRepository.findById(token).isPresent()) {
            throw new TokenNotFoundException();
        }
    }

    @Override
    public Token getTokenById(UUID token) {
        return authRepository
                .findById(token)
                .orElseThrow(UserUnauthorisedException::new);
    }
}