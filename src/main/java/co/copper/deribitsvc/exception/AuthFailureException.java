package co.copper.deribitsvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthFailureException extends RuntimeException{

	public AuthFailureException() {
		super();
	}

	public AuthFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthFailureException(String message) {
		super(message);
	}

	public AuthFailureException(Throwable cause) {
		super(cause);
	}

}
