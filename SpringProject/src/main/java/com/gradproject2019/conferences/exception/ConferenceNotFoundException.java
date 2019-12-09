package com.gradproject2019.conferences.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Conference not found.")
public class ConferenceNotFoundException extends RuntimeException {
}