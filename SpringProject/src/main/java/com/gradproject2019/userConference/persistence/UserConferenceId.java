package com.gradproject2019.userConference.persistence;


import java.io.Serializable;


public class UserConferenceId implements Serializable {

    private Long userId;

    private Long conferenceId;

    public UserConferenceId(Long userId, Long conferenceId) {
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    public UserConferenceId() {
    }

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
}
