package com.gradproject2019.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="Token not found.")
public class TokenNotFoundException extends RuntimeException {
}
