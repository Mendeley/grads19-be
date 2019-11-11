package com.gradproject2019.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="Invalid password.")
public class InvalidPasswordFormatException extends RuntimeException {
}