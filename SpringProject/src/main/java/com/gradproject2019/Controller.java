package com.gradproject2019;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping(path="/all")
public class Controller {

    @Autowired
    private ConferenceRepository conferenceRepository;

}
