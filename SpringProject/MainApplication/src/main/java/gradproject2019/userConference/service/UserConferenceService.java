package gradproject2019.userConference.service;

import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.userConference.data.UserConferenceRequestDto;
import gradproject2019.userConference.data.UserConferenceResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto);

    List<ConferenceResponseDto> getConferenceByUserId(UUID token, Long userId);

    //void deleteInterest(UUID token, Long conferenceId);
}