package co.copper.deribitsvc.connector.deribit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorBody {
	private String message;

	public String getMessage() {
		return message;
	}
}
