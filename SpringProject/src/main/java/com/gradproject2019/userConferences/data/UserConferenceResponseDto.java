package com.gradproject2019.userConferences.data;

import com.gradproject2019.userConferences.persistance.UserConference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
public class UserConferenceResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long conferenceId;

    public UserConferenceResponseDto() {
    }

    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public Long getConferenceId() { return conferenceId; }

    public UserConferenceResponseDto from(UserConference userConference) {
        return UserConferenceResponseDtoBuilder.anUserConferenceResponseDto()
                .withId(userConference.getId())
                .withUserId(userConference.getUserId())
                .withConferenceId(userConference.getConferenceId())
                .build();
    }


    public static final class UserConferenceResponseDtoBuilder {
        private Long id;
        private Long userId;
        private Long conferenceId;

        private UserConferenceResponseDtoBuilder() {
        }

        public static UserConferenceResponseDtoBuilder anUserConferenceResponseDto() {
            return new UserConferenceResponseDtoBuilder();
        }

        public UserConferenceResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
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
            userConferenceResponseDto.id = this.id;
            userConferenceResponseDto.conferenceId = this.conferenceId;
            return userConferenceResponseDto;
        }
    }
}
