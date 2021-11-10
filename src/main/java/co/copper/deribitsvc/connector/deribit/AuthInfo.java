package co.copper.deribitsvc.connector.deribit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthInfo {
	private String accessToken;
	private int expiresIn;
	private String refreshToken;
	private String scope;
	private String state;
	private String tokenType;

	public String getAcccessToken() {
		return accessToken;
	}

	@JsonSetter("access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public String getState() {
		return state;
	}

	public String getTokenType() {
		return tokenType;
	}

	@JsonSetter("expires_in")
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@JsonSetter("refresh_token")
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonSetter("token_type")
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
