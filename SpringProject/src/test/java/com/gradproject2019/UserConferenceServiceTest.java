package com.gradproject2019;

import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.userConference.service.UserConferenceService;
import com.gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserConferenceServiceTest {

    @InjectMocks
    private UserConferenceServiceImpl userConferenceService;

    @Mock
    private UserConferenceRepository userConferenceRepository;

    @Test
    public void shouldSaveInterestInDatabase() {
        //given
        //given(userConferenceRepository.linkUserConference(1,1));

        //when

    }



}
