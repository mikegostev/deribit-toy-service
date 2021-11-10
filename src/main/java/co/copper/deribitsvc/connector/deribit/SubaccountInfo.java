package co.copper.deribitsvc.connector.deribit;

import java.util.Map;

public class SubaccountInfo {
	private String email;
	private Map<String, AssetInfo> portfolio;
	private long id;

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public Map<String, AssetInfo> getPortfolio() {
		return portfolio;
	}

}
