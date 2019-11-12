package com.gradproject2019.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason="Password incorrect.")
public class IncorrectPasswordException extends RuntimeException{
}
