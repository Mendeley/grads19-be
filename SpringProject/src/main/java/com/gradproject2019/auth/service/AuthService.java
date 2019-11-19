package com.gradproject2019.auth.service;

import com.gradproject2019.auth.data.LoginDto;
import com.gradproject2019.auth.persistance.Token;

public interface AuthService {

    Token login(LoginDto loginDto);
}
