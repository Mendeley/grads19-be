package gradproject2019.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid user credentials.")
public class InvalidCredentialsException extends RuntimeException {
}