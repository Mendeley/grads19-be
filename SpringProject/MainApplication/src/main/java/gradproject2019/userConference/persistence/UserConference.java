package gradproject2019.userConference.persistence;

import gradproject2019.userConference.data.UserConferenceRequestDto;
import javax.persistence.*;

@Entity
@Table(name = "user_conferences")
@IdClass(UserConferenceId.class)
public class UserConference {

    @Id
    private Long userId;

    @Id
    private Long conferenceId;

    public UserConference(Long userId, Long conferenceId) {
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    public UserConference() {
    }

    @ManyToMany
    @JoinTable(
            name = "user_conferences",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "conference_id")}
    )

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public UserConference from(UserConferenceRequestDto userConferenceRequestDto) {
        return anUserConference()
                .withUserId(userConferenceRequestDto.getUserId())
                .withConferenceId(userConferenceRequestDto.getConferenceId())
                .build();
    }

    public static final class UserConferenceBuilder {
        private Long userId;
        private Long conferenceId;

        public UserConferenceBuilder() {
        }

        public static UserConferenceBuilder anUserConference() {
            return new UserConferenceBuilder();
        }

        public UserConferenceBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserConferenceBuilder withConferenceId(Long conferenceId) {
            this.conferenceId = conferenceId;
            return this;
        }

        public UserConference build() {
            UserConference userConference = new UserConference();
            userConference.setUserId(userId);
            userConference.setConferenceId(conferenceId);
            return userConference;
        }
    }
}