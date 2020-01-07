package gradproject2019.auth.service;

import gradproject2019.auth.data.LoginDto;
import gradproject2019.auth.persistence.Token;

import java.util.UUID;

public interface AuthService {

    Token login(LoginDto loginDto);

    void logout(UUID token);

    void checkTokenExists(UUID token);

    Token getTokenById(UUID token);
}