package com.gradproject2019;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.stereotype.Controller
//@RequestMapping(path="/all")
public class Controller {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping(path="/ListOfConferences")
    public @ResponseBody Iterable<Conference> getAllUsers(){
        return conferenceRepository.findAll();
    }

}