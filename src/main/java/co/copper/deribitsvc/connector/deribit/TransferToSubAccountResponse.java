package co.copper.deribitsvc.connector.deribit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.copper.deribitsvc.model.TransferToSubaccountReport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferToSubAccountResponse{
	private TransferToSubaccountReport result;
	
	public TransferToSubaccountReport getResult() {
		return result;
	}
}
