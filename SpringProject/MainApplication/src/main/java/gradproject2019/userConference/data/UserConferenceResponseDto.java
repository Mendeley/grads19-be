package gradproject2019.userConference.data;

import gradproject2019.userConference.persistence.UserConference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class UserConferenceResponseDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long conferenceId;

    public UserConferenceResponseDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public UserConferenceResponseDto from(UserConference userConference) {
        return UserConferenceResponseDtoBuilder
                .anUserConferenceResponseDto()
                .withUserId(userConference.getUserId())
                .withConferenceId(userConference.getConferenceId())
                .build();
    }

    public static final class UserConferenceResponseDtoBuilder {
        private Long userId;
        private Long conferenceId;

        private UserConferenceResponseDtoBuilder() {
        }

        public static UserConferenceResponseDtoBuilder anUserConferenceResponseDto() {
            return new UserConferenceResponseDtoBuilder();
        }

        public UserConferenceResponseDtoBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserConferenceResponseDtoBuilder withConferenceId(Long conferenceId) {
            this.conferenceId = conferenceId;
            return this;
        }

        public UserConferenceResponseDto build() {
            UserConferenceResponseDto userConferenceResponseDto = new UserConferenceResponseDto();
            userConferenceResponseDto.userId = this.userId;
            userConferenceResponseDto.conferenceId = this.conferenceId;
            return userConferenceResponseDto;
        }
    }
}