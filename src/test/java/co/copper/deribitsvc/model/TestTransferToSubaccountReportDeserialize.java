package co.copper.deribitsvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestTransferToSubaccountReportDeserialize {
	public static final String sampleJson = """
	{
	    "updated_timestamp": 1636470725631,
	    "type": "subaccount",
	    "state": "confirmed",
	    "source": 34865,
	    "other_side": "MiGee_Sub1",
	    "note": "",
	    "id": 259142,
	    "direction": "payment",
	    "currency": "BTC",
	    "created_timestamp": 1636470725631,
	    "amount": 0.01
	  }
	  """;
	
	@Test
	void testDeserialize() throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		TransferToSubaccountReport w = mapper.readValue(sampleJson, TransferToSubaccountReport.class);
		
		assertEquals("BTC", w.currency());
		assertEquals(TransferState.confirmed, w.state());
	}
}
