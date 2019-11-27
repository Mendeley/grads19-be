package com.gradproject2019.utils;

import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.users.persistance.User;
import com.gradproject2019.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.UUID;

public class TestUtils {
    @Autowired
    public AuthRepository authRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ConferenceRepository conferenceRepository;

    @Autowired
    public TestRestTemplate restTemplate;

    private User user;
    public User savedUser;
    private String hashedPassword;
    public Token testToken;

    public void clearRepositories() {
        authRepository.deleteAll();
        userRepository.deleteAll();
        conferenceRepository.deleteAll();
    }

    public void universalSetUp() {
        clearRepositories();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        hashedPassword = AuthUtils.hash("P455w0rd!");
        user = new User(1L, "KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", hashedPassword, "Botanist");
        savedUser = userRepository.saveAndFlush(user);
        testToken = new Token(savedUser.getId(), UUID.randomUUID());
        authRepository.saveAndFlush(testToken);
    }
}