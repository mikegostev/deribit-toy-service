package co.copper.deribitsvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestWithdrawDeserialize {

	public static final String sampleJson = """
			{
			    "updated_timestamp": 1636471159532,
			    "transaction_id": null,
			    "state": "unconfirmed",
			    "priority": 1.5,
			    "note": "",
			    "id": 16987,
			    "fee": 0.0002,
			    "currency": "BTC",
			    "created_timestamp": 1636471159532,
			    "confirmed_timestamp": null,
			    "amount": 0.01,
			    "address": "2MuVeA4UdKkZTmKz8t8WRoWrUhveCxVNjDh"
			 }""";

	@Test
	void testDeserialize() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		WithdrawReport w = mapper.readValue(sampleJson, WithdrawReport.class);

		assertEquals("BTC", w.currency());
		assertEquals(TransferState.unconfirmed, w.state());
	}

}
