package com.gradproject2019.conferences.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="Conferences not found.")
public class ConferencesNotFoundException extends RuntimeException {
}
