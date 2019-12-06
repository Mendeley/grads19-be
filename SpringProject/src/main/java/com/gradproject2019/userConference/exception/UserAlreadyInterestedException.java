package com.gradproject2019.userConference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Conference already favourited.")
public class UserAlreadyInterestedException extends RuntimeException {
}