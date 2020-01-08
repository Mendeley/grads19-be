package gradproject2019.utils;

import gradproject2019.auth.persistence.Token;
import gradproject2019.auth.repository.AuthRepository;
import gradproject2019.auth.service.AuthService;
import gradproject2019.conferences.persistence.Conference;
import gradproject2019.conferences.repository.ConferenceRepository;
import gradproject2019.userConference.persistence.UserConference;
import gradproject2019.userConference.repository.UserConferenceRepository;
import gradproject2019.userConference.service.UserConferenceService;
import gradproject2019.users.persistence.User;
import gradproject2019.users.repository.UserRepository;
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
    public AuthService authService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ConferenceRepository conferenceRepository;

    @Autowired
    public UserConferenceRepository userConferenceRepository;

    @Autowired
    public UserConferenceService userConferenceService;

    @Autowired
    public TestRestTemplate restTemplate;

    private User user;
    private Conference conference;
    public User savedUser;
    public User userWithManager;
    public User manager;
    public Conference savedConference;
    public UserConference userConference;
    private String hashedPassword;
    public Token testToken;
    public HttpHeaders passingHeaders;
    public HttpHeaders managerPassingHeaders;
    public HttpHeaders failingHeaders;
    public User savedUserWithManager;
    public User savedManager;
    public Token managerToken;
    public Token userWithManagerToken;

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
        manager = new User(3L, "Manager", "Manager", "Manager", "manager@gmail.com", hashedPassword, "Manager", null);
        conference = new Conference(1L, "GraceCon", Instant.now(), "Manchester", "COol", "Sophia");
        savedUser = userRepository.saveAndFlush(user);
        savedManager = userRepository.saveAndFlush(manager);
        userWithManager = new User(2L, "userWithManager", "userWithManager", "userWithManager", "userWithManager@gmail.com", hashedPassword, "userWithManager", savedManager.getId());
        savedUserWithManager = userRepository.saveAndFlush(userWithManager);
        savedConference = conferenceRepository.saveAndFlush(conference);
        userConference = new UserConference(savedUser.getId(), savedConference.getId());
        userConferenceRepository.saveAndFlush(userConference);
        testToken = new Token(savedUser.getId(), UUID.randomUUID());
        managerToken = new Token(savedManager.getId(), UUID.randomUUID());
        userWithManagerToken = new Token(savedUserWithManager.getId(), UUID.randomUUID());
        authRepository.saveAndFlush(testToken);
        authRepository.saveAndFlush(managerToken);
        constructPassingHeader(testToken.getToken());
        constructManagerPassingHeader(managerToken.getToken());
        constructFailingHeader();
    }

    private void constructPassingHeader(UUID token) {
        passingHeaders = new HttpHeaders();
        passingHeaders.add(HttpHeaders.AUTHORIZATION, token.toString());
    }

    private void constructManagerPassingHeader(UUID token) {
        managerPassingHeaders = new HttpHeaders();
        managerPassingHeaders.add(HttpHeaders.AUTHORIZATION, token.toString());
    }

    private void constructFailingHeader() {
        failingHeaders = new HttpHeaders();
        failingHeaders.add(HttpHeaders.AUTHORIZATION, UUID.randomUUID().toString());
    }
}