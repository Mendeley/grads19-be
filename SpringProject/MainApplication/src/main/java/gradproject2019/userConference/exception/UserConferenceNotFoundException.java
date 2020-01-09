package gradproject2019.userConference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Conference has not been favourited yet.")
public class UserConferenceNotFoundException extends RuntimeException {
}