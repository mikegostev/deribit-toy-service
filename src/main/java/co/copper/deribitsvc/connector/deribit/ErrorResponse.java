package co.copper.deribitsvc.connector.deribit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
	private ErrorBody error;
	private String jsonrpc;
	
	public String getMessage() {
		return error.getMessage();
	}

	public ErrorBody getError() {
		return error;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

}
