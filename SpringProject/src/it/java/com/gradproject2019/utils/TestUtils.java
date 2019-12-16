package com.gradproject2019.utils;

import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.conferences.persistence.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.users.persistence.User;
import com.gradproject2019.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.time.Instant;
import java.util.UUID;

public class TestUtils {
    @Autowired
    public AuthRepository authRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ConferenceRepository conferenceRepository;

    @Autowired
    public UserConferenceRepository userConferenceRepository;

    @Autowired
    public TestRestTemplate restTemplate;

    private User user;
    private Conference conference;
    public User savedUser;
    public Conference savedConference;
    public UserConference savedUserConference;
    private String hashedPassword;
    public Token testToken;
    public HttpHeaders passingHeaders;
    public HttpHeaders failingHeaders;

    public void clearRepositories() {
        authRepository.deleteAll();
        userConferenceRepository.deleteAll();
        userRepository.deleteAll();
        conferenceRepository.deleteAll();
    }

    public void universalSetUp() {
        clearRepositories();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        hashedPassword = AuthUtils.hash("P455w0rd!");
        user = new User(1L, "KaramsCoolUsername", "Karam", "Kapoor", "KSinghK@gmail.com", hashedPassword, "Botanist", null);
        conference = new Conference(1L, "GraceCon", Instant.now(), "Manchester", "COol", "Sophia");
        savedUser = userRepository.saveAndFlush(user);
        savedConference = conferenceRepository.saveAndFlush(conference);
        testToken = new Token(savedUser.getId(), UUID.randomUUID());
        authRepository.saveAndFlush(testToken);
        savedUserConference = new UserConference(savedUser.getId(),savedConference.getId());
        userConferenceRepository.saveAndFlush(savedUserConference);
        constructPassingHeader(testToken.getToken());
        constructFailingHeader();
    }

    private void constructPassingHeader(UUID token) {
        passingHeaders = new HttpHeaders();
        passingHeaders.add(HttpHeaders.AUTHORIZATION, token.toString());
    }

    private void constructFailingHeader() {
        failingHeaders = new HttpHeaders();
        failingHeaders.add(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString());
    }
}