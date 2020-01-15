package gradproject2019.conferences.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid entry in conference field.")
public class InvalidConferenceFieldException extends RuntimeException {
}