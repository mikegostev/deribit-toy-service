package co.copper.deribitsvc.connector.deribit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
	private AuthInfo result;

	public AuthInfo getResult() {
		return result;
	}

	public void setResult(AuthInfo result) {
		this.result = result;
	}
}
