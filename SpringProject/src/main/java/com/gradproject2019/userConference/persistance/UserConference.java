package com.gradproject2019.userConference.persistance;


import javax.persistence.*;

@Entity
@Table(name = "user_conferences")
public class UserConference {

//    private Long id;

    private Long userId;

    private Long conferenceId;

    public UserConference() {
    }

//    public Long getId() { return id; }

//    public void setId(Long id) { id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getConferenceId() { return conferenceId; }

    public void setConferenceId(Long conferenceId) { this.conferenceId = conferenceId; }


    public static final class UserConferenceBuilder {
        private Long userId;
        private Long conferenceId;
//        private Long id;

        public UserConferenceBuilder() {
        }

        public static UserConferenceBuilder anUserConference() {
            return new UserConferenceBuilder();
        }

//        public UserConferenceBuilder withId(Long id) {
//            this.id = id;
//            return this;
//        }


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
//            userConference.setId(id);
            userConference.setUserId(userId);
            userConference.setConferenceId(conferenceId);
            return userConference;
        }
    }
}
