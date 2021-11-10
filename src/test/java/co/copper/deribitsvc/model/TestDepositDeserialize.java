package co.copper.deribitsvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDepositDeserialize {
	public static final String sampleJson = """
				{
			       "updated_timestamp": 1636202251940,
			       "transaction_id": "c72f98ddea91ce4a7c56286f660cd28830fc7bb0dbdac7bed3fa6ef37b40bbcd",
			       "state": "completed",
			       "received_timestamp": 1636202211508,
			       "note": "",
			       "currency": "BTC",
			       "amount": 1,
			       "address": "2N725pC8LMXVcdior9T1d1wEf9smCFnrQgc"
			     }
			     """;
	
	@Test
	void testDeserialize() throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		Deposit w = mapper.readValue(sampleJson, Deposit.class);
		
		assertEquals("BTC", w.currency());
		assertEquals(TransferState.completed, w.state());
	}
}
