package co.copper.deribitsvc.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Withdrawal(
		String address,
		BigDecimal amount,
		long confirmed_timestamp,
		long created_timestamp,
		String currency,
		BigDecimal fee,
		long id,
		float priority,
		TransferState state,
		String transaction_id,
		long updated_timestamp
		) {
	
}
