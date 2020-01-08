package grads19.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static grads19.batch.Conference.ConferenceBuilder.aConference;

@Component
public class ConferenceProcessor implements ItemProcessor<ConferenceDTO, Conference> {

    @Override
    public Conference process(final ConferenceDTO conferenceDto) {

        return aConference()
                .withName(conferenceDto.getName())
                .withCity(conferenceDto.getCity())
                .withDescription(conferenceDto.getDescription())
                .withTopic(conferenceDto.getTopic())
                .build();
    }
}