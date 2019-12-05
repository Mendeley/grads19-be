package com.gradproject2019.userConference.persistance;

import java.io.Serializable;

public class UserConferenceId implements Serializable {

    private Long userId;

    private Long conferenceId;

    public UserConferenceId(Long userId, Long conferenceId) {
        this.userId = userId;
        this.conferenceId = conferenceId;
    }
}
