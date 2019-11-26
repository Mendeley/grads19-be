package com.gradproject2019.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason="Credentials are already in use.")
public class UserInfoExistsException extends RuntimeException {
}