package co.copper.deribitsvc.connector.deribit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResult<T> {
	private List<T> data;

	public List<T> getData() {
		return data;
	}

}
