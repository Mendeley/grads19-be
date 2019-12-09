package com.gradproject2019.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason="User forbidden to perform action.")
public class UserForbiddenException extends RuntimeException {
}