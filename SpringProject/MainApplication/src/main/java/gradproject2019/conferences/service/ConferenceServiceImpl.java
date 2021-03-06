package gradproject2019.conferences.service;


import gradproject2019.auth.service.AuthServiceImpl;
import gradproject2019.conferences.data.ConferencePatchRequestDto;
import gradproject2019.conferences.data.ConferenceRequestDto;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.exception.ConferenceNotFoundException;
import gradproject2019.conferences.exception.InvalidConferenceFieldException;
import gradproject2019.conferences.persistence.Conference;
import gradproject2019.conferences.repository.ConferenceRepository;
import gradproject2019.elasticsearch.repository.ConferenceSearchRepository;
import gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static gradproject2019.conferences.data.ConferenceRequestDto.from;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    private AuthServiceImpl authServiceImpl;
    private ConferenceSearchRepository conferenceSearchRepository;

    private UserConferenceServiceImpl userConferenceServiceImpl;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, AuthServiceImpl authServiceImpl, ConferenceSearchRepository conferenceSearchRepository, @Lazy UserConferenceServiceImpl userConferenceServiceImpl) {
        this.conferenceRepository = conferenceRepository;
        this.authServiceImpl = authServiceImpl;
        this.userConferenceServiceImpl = userConferenceServiceImpl;
        this.conferenceSearchRepository = conferenceSearchRepository;
    }

    @Override
    public ConferenceResponseDto editConference(UUID token, Long conferenceId, ConferencePatchRequestDto conferencePatch) {
        authServiceImpl.getTokenById(token);
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
        authServiceImpl.getTokenById(token);
        checkConferenceExists(conferenceId);
        userConferenceServiceImpl.deleteByConferenceId(conferenceId);
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
        authServiceImpl.getTokenById(token);
        Conference conference = from(conferenceRequestDto);

        checkNotInPast(conference.getDateTime());
        return new ConferenceResponseDto().from(conferenceRepository.saveAndFlush(conference));
    }


    @Override

    public List<ConferenceResponseDto> findByConferenceName(String name, Integer page, Integer size) {
        return conferenceSearchRepository.findByName(name,PageRequest.of(page, size))
                .get()
                .map(c -> new ConferenceResponseDto().esFrom(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceResponseDto> findByConferenceCity(String city, Integer page, Integer size) {
        return conferenceSearchRepository.findByCity(city, PageRequest.of(page, size))
                .get()
                .map(c -> new ConferenceResponseDto().esFrom(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceResponseDto> findByConferenceDescription(String description, Integer page, Integer size) {
        return conferenceSearchRepository.findByDescription(description, PageRequest.of(page, size))
                .get()
                .map(c -> new ConferenceResponseDto().esFrom(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConferenceResponseDto> findByConferenceTopic(String topic, Integer page, Integer size) {
        return conferenceSearchRepository.findByTopic(topic, PageRequest.of(page, size))
                .get()
                .map(c -> new ConferenceResponseDto().esFrom(c))
                .collect(Collectors.toList());
    }

    private void checkNotInPast(Instant dateTime) {
        if (dateTime == null) {
            return;
        }
        if (!dateTime.isAfter(Instant.now())) {
            throw new InvalidConferenceFieldException();
        }
    }


    private void checkConferenceExists(Long conferenceId) {
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException();
        }
    }
}