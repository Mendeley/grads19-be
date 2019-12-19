package com.gradproject2019.userConference.data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class UserConferenceRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long conferenceId;

    public UserConferenceRequestDto(@NotNull Long userId, @NotNull Long conferenceId) {
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    public UserConferenceRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public static final class UserConferenceRequestDtoBuilder {
        private Long userId;
        private Long conferenceId;

        public UserConferenceRequestDtoBuilder() {
        }

        public static UserConferenceRequestDtoBuilder anUserConferenceRequestDto() {
            return new UserConferenceRequestDtoBuilder();
        }

        public UserConferenceRequestDtoBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserConferenceRequestDtoBuilder withConferenceId(Long conferenceId) {
            this.conferenceId = conferenceId;
            return this;
        }

        public UserConferenceRequestDto build() {
            UserConferenceRequestDto userConferenceRequestDto = new UserConferenceRequestDto();
            userConferenceRequestDto.userId = this.userId;
            userConferenceRequestDto.conferenceId = this.conferenceId;
            return userConferenceRequestDto;
        }
    }
}