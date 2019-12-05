package com.gradproject2019.userConference.data;

import com.gradproject2019.userConference.persistance.UserConference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import static com.gradproject2019.userConference.persistance.UserConference.UserConferenceBuilder.anUserConference;

@Validated
public class UserConferenceRequestDto {
//    @NotNull
//    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long conferenceId;

    public UserConferenceRequestDto() {
    }

//    public Long getId() { return id;}
//
//    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public Long getConferenceId() { return conferenceId; }

    public static UserConference from(UserConferenceRequestDto userConferenceRequestDto) {
        return anUserConference()
                //.withId(userConferenceRequestDto.getId())
                .withUserId(userConferenceRequestDto.getUserId())
                .withConferenceId(userConferenceRequestDto.getConferenceId())
                .build();
    }


    public static final class UserConferenceRequestDtoBuilder {
        //private Long id;
        private Long userId;
        private Long conferenceId;

        public UserConferenceRequestDtoBuilder() {
        }

        public static UserConferenceRequestDtoBuilder anUserConferenceRequestDto() {
            return new UserConferenceRequestDtoBuilder();
        }

//        public UserConferenceRequestDtoBuilder withId(Long id) {
//            this.id = id;
//            return this;
//        }

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
           // userConferenceRequestDto.id = this.id;
            userConferenceRequestDto.userId = this.userId;
            userConferenceRequestDto.conferenceId = this.conferenceId;
            return userConferenceRequestDto;
        }
    }
}
