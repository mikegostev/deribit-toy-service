package co.copper.deribitsvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiCallException extends RuntimeException{

	public ApiCallException() {
		super();
	}

	public ApiCallException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiCallException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiCallException(String message) {
		super(message);
	}

	public ApiCallException(Throwable cause) {
		super(cause);
	}

}
