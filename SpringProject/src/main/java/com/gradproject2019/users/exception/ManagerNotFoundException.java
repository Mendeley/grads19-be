package com.gradproject2019.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="Manager not found.")
public class ManagerNotFoundException extends RuntimeException{
}
