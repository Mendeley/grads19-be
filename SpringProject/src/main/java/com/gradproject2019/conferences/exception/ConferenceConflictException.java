package com.gradproject2019.conferences.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason="Conference already exists.")
public class ConferenceConflictException extends RuntimeException {
}
