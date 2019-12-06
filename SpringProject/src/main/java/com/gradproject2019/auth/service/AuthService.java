package com.gradproject2019.auth.service;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;

import java.util.UUID;

public interface AuthService {

    Token login(LoginDto loginDto);

    void logout(UUID token);

    void checkTokenExists(UUID token);
}