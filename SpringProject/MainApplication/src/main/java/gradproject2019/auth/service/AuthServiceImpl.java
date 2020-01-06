package gradproject2019.auth.service;

import gradproject2019.auth.data.LoginDto;
import gradproject2019.auth.exception.TokenNotFoundException;
import gradproject2019.auth.exception.UserUnauthorisedException;
import gradproject2019.auth.persistence.Token;
import gradproject2019.auth.repository.AuthRepository;
import gradproject2019.users.exception.InvalidCredentialsException;
import gradproject2019.users.persistence.User;
import gradproject2019.users.repository.UserRepository;
import gradproject2019.utils.AuthUtils;
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
                .orElseThrow(InvalidCredentialsException::new);
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

    public Token getTokenById(UUID token) {
        return authRepository
                .findById(token)
                .orElseThrow(UserUnauthorisedException::new);
    }
}