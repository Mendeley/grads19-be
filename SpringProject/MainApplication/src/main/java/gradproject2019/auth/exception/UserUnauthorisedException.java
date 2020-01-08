package gradproject2019.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User unauthorized to perform action.")
public class UserUnauthorisedException extends RuntimeException {
}