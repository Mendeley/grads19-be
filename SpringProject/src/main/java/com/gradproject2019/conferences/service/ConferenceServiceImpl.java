package com.gradproject2019.conferences.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gradproject2019.conferences.data.ConferenceRequestDto.from;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    private AuthServiceImpl authServiceImpl;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, AuthServiceImpl authServiceImpl) {
        this.conferenceRepository = conferenceRepository;
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public ConferenceResponseDto editConference(UUID token, Long conferenceId, ConferencePatchRequestDto conferencePatch) {
        checkUserAuthorised(token);
        checkConferenceExists(conferenceId);
        checkNotInPast(conferencePatch.getDateTime());

        conferenceRepository.updateConference(conferenceId, conferencePatch.getName(), conferencePatch.getDateTime(), conferencePatch.getCity(), conferencePatch.getDescription(), conferencePatch.getTopic());

        return getConferenceById(conferenceId);
    }

    @Override
    public List<ConferenceResponseDto> getAllConferences() {
        return conferenceRepository.findAll().stream()
                .map(conference -> new ConferenceResponseDto().from(conference))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteConference(UUID token, Long conferenceId) {
        checkUserAuthorised(token);
        checkConferenceExists(conferenceId);
        conferenceRepository.deleteById(conferenceId);
    }

    @Override
    public ConferenceResponseDto getConferenceById(Long conferenceId) {
        return new ConferenceResponseDto().from(conferenceRepository
                .findById(conferenceId)
                .orElseThrow(ConferenceNotFoundException::new));
    }

    @Override
    public ConferenceResponseDto saveConference(UUID token, ConferenceRequestDto conferenceRequestDto) {
        checkUserAuthorised(token);
        Conference conference = from(conferenceRequestDto);

        checkNotInPast(conference.getDateTime());
        return new ConferenceResponseDto().from(conferenceRepository.saveAndFlush(conference));
    }

    private void checkNotInPast(Instant dateTime) {
        try {
            if (!dateTime.isAfter(Instant.now())) {
                throw new InvalidConferenceFieldException();
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void checkConferenceExists(Long conferenceId) {
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException();
        }
    }

    private void checkUserAuthorised(UUID token) {
        try {
            authServiceImpl.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
    }
}