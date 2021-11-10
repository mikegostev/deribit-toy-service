package co.copper.deribitsvc.connector.deribit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericDataResponse<T> {
	private GenericResult<T> result;

	List<T> getData(){
		return result.getData();
	}
	
	@JsonSetter
	public void setResult(GenericResult<T> result) {
		this.result = result;
	}
}
